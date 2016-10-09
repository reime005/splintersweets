/*
 * Copyright (c) 2016. Marius Reimer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.reimerm.splintersweets

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.games.Games
import de.reimerm.splintersweets.utils.GameManager
import de.reimerm.splintersweets.utils.GameSettings

/**
 * Created by mariu on 19-Apr-16.
 */
object LoginHandler {

    val REQUEST_RESOLVE_ERROR = 1001
    val REQUEST_LEADERBOARD = 1002
    private val TAG = "LoginHandler"
    private var mGoogleApiClient: GoogleApiClient? = null
    private var context: Context? = null
    private lateinit var view: View

    private var mResolvingError = false

    fun getmGoogleApiClient(): GoogleApiClient? {
        return mGoogleApiClient
    }

    fun setmResolvingError(mResolvingError: Boolean) {
        this.mResolvingError = mResolvingError
    }

    fun setContext(context: Context, view: View) {
        this.context = context
        this.view = view
        startApiClient()
    }

    fun startApiClient() {
        if (mGoogleApiClient == null) {
            val playServiceListener = PlayServiceListener()
            mGoogleApiClient = GoogleApiClient.Builder(context!!)
                    .addConnectionCallbacks(playServiceListener)
                    .addOnConnectionFailedListener(playServiceListener)
                    .addApi(Games.API)
                    .addScope(Games.SCOPE_GAMES)
                    .setViewForPopups(view)
                    .build()
        }
    }

    fun submitScore() {
        if (isConnected) {
            Games.Leaderboards.submitScore(mGoogleApiClient, GameSettings.PLAY_SERVICE_LEADERBOARD, GameManager.score.toLong())
        }
    }

    fun login() {
        if (!isConnected) {
            mGoogleApiClient?.connect()
        }
    }

    fun logout() {
        if (isConnected) {
            mGoogleApiClient?.disconnect()
            Toast.makeText(context, R.string.logout_succeed, Toast.LENGTH_SHORT).show()
        }
    }

    fun callLeaderBoard(targetActivity: Activity) {
        login()
        if (isConnected) {
            targetActivity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                    GameSettings.PLAY_SERVICE_LEADERBOARD), REQUEST_LEADERBOARD)
        }
    }

    val isConnected: Boolean
        get() = mGoogleApiClient != null && mGoogleApiClient!!.isConnected

    private class PlayServiceListener : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
        override fun onConnected(bundle: Bundle?) {
            Log.d(TAG, "onConnected")
            Toast.makeText(context, R.string.login_succeed, Toast.LENGTH_SHORT).show()
            GameManager.listener?.loadHighScore()
        }

        override fun onConnectionSuspended(i: Int) {
            Log.d(TAG, "onConnectionSuspended")
            // Attempt to reconnect
            mGoogleApiClient?.connect()
        }

        override fun onConnectionFailed(connectionResult: ConnectionResult) {
            Log.d(TAG, "onConnectionFailed")
            if (mResolvingError) {
                // Already attempting to resolve an error.
                return
            } else if (connectionResult.hasResolution()) {
                try {
                    mResolvingError = true
                    connectionResult.startResolutionForResult(context as Activity, REQUEST_RESOLVE_ERROR)
                } catch (e: IntentSender.SendIntentException) {
                    // There was an error with the resolution intent. Try again.
                    mGoogleApiClient?.connect()
                }

            } else {
                // Show dialog using GoogleApiAvailability.getErrorDialog()
                Toast.makeText(context, R.string.login_failed, Toast.LENGTH_SHORT).show()
                mResolvingError = true
            }
        }
    }
}
