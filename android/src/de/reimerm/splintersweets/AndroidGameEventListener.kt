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

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.games.Games
import com.google.android.gms.games.leaderboard.LeaderboardVariant
import de.reimerm.splintersweets.utils.GameEventListener
import de.reimerm.splintersweets.utils.GameManager
import de.reimerm.splintersweets.utils.GameSettings

/**
 * Created by Marius Reimer on 07-Oct-16.
 */
class AndroidGameEventListener : GameEventListener {

    private lateinit var context: Context
    private val SHOW = 1
    private val HIDE = 0
    private var adView: AdView? = null
    private var interstitialAd: InterstitialAd

    private var handler: Handler? = null

    constructor(adView: AdView?, context: Context) {
        this.adView = adView
        this.context = context

        interstitialAd = InterstitialAd(context)
        interstitialAd.adUnitId = GameSettings.ADMOB_INTERSTITIAL_ID
        interstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                requestNewInterstitial()
                super.onAdClosed()
            }
        }

        requestNewInterstitial()

        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    HIDE -> {
                        adView?.visibility = View.GONE
                    }
                    SHOW -> {
                        adView?.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun requestNewInterstitial() {
        val adRequestBuilder = AdRequest.Builder()
        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)

        interstitialAd.loadAd(adRequestBuilder.build())
    }

    override fun login() {
        if (!LoginHandler.isConnected) {
            LoginHandler.login()
        }
    }

    override fun displayLeaderBoard() {
        login()
        LoginHandler.callLeaderBoard(context as AndroidLauncher)
    }

    override fun showAd() {
        handler?.sendEmptyMessage(SHOW)
    }

    override fun hideAd() {
        handler?.sendEmptyMessage(HIDE)
    }

    override fun submitScore() {
        LoginHandler.submitScore()
    }

    override fun showInterstitialAd() {
        (context as AndroidLauncher).runOnUiThread {
            if (interstitialAd.isLoaded) {
                interstitialAd.show()
            }
        }
    }

    override fun loadHighScore() {
        if (LoginHandler.isConnected) {
            Games.Leaderboards.loadCurrentPlayerLeaderboardScore(LoginHandler.getmGoogleApiClient(), GameSettings.PLAY_SERVICE_LEADERBOARD,
                    LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback { loadPlayerScoreResult ->
                var score: Long = 0
                if (loadPlayerScoreResult.score != null) {
                    score = loadPlayerScoreResult.score.rawScore
                }

                Log.d("AndroidLauncher", "onResult score: " + score)

                if (score > 0 && score != GameManager.score) {
                    GameManager.onlineScore = score
                }
            }
        }
    }
}