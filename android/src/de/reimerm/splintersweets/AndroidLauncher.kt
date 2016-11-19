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
import android.content.Intent
import android.os.Bundle
import android.support.multidex.MultiDex
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.Toast
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.games.GamesActivityResultCodes
import de.reimerm.splintersweets.main.MainGame
import de.reimerm.splintersweets.utils.GameManager
import de.reimerm.splintersweets.utils.GameSettings

class AndroidLauncher : AndroidApplication() {

    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)

        val gameView = initializeForView(MainGame, AndroidApplicationConfiguration())
        LoginHandler.setContext(context, gameView)

        val layout = RelativeLayout(this)
        adView = AdView(this)
        adView?.adSize = AdSize.SMART_BANNER
        adView?.adUnitId = GameSettings.ADMOB_BANNER_ID

        GameManager.listener = AndroidGameEventListener(adView, context)

        val builder = AdRequest.Builder()
        builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)

        adView?.loadAd(builder.build())

        layout.addView(gameView)

        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

        layout.addView(adView, layoutParams)

        setContentView(layout)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LoginHandler.REQUEST_RESOLVE_ERROR) {
            LoginHandler.setmResolvingError(false)
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                LoginHandler.login()
            } else if (resultCode == RESULT_CANCELED) {
                // The account picker dialog closed without selecting an account.
                // Notify users that they must pick an account to proceed.
                Toast.makeText(this, R.string.login_failed, Toast.LENGTH_SHORT).show()
            }
        }

        if (resultCode == GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED && requestCode == LoginHandler.REQUEST_LEADERBOARD) {
            // force a disconnect to sync up state, ensuring that mClient reports "not connected"
            LoginHandler.logout()
        }
    }

    override fun onBackPressed() {
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        MultiDex.install(this)
    }
}
