package de.reimerm.splintersweets

import apple.uikit.UIViewController
import de.reimerm.splintersweets.leaderboards.Leaderboard
import de.reimerm.splintersweets.utils.GameEventListener
import de.reimerm.splintersweets.utils.GameManager
import de.reimerm.splintersweets.utils.GameSettings
import org.moe.googlemobileads.GADBannerView
import org.moe.googlemobileads.GADInterstitial
import org.moe.gpg.GPGLauncherController
import org.moe.gpg.GPGLeaderboard
import org.moe.gpg.GPGManager
import org.moe.gpg.GPGScore

class IOSGameEventListener(private val adView: GADBannerView?, private val gadInterstitial: GADInterstitial, private val uiViewController: UIViewController?) : GameEventListener {

    override fun submitScore() {
        Log.d("submitScore()")
        if ((GPGManager.sharedInstance() as GPGManager).isSignedIn) {
            Log.d("connected")
            val score = GPGScore.scoreWithLeaderboardId(Leaderboard.Scores.id)
            score.setValue(GameManager.score)
            score.submitScoreWithCompletionHandler { gpgScoreReport, nsError ->
                if (nsError != null) {
                    Log.d("error on submitting high score")
                } else {
                    Log.d("successfully submitted high score")
                }
            }
        }
    }

    override fun login() {
        if (!(GPGManager.sharedInstance() as GPGManager).isSignedIn) {
            (GPGManager.sharedInstance() as GPGManager).signInWithClientIDSilently(GameSettings.CLIENT_ID, false)
        }
    }

    override fun displayLeaderBoard() {
        if ((GPGManager.sharedInstance() as GPGManager).isSignedIn) {
            (GPGLauncherController.sharedInstance() as GPGLauncherController).presentLeaderboardWithLeaderboardId(Leaderboard.Scores.id)
        } else {
            (GPGManager.sharedInstance() as GPGManager).signInWithClientIDSilently(GameSettings.CLIENT_ID, false)
        }
    }

    override fun hideAd() {
        Log.d("hideAd()")
        adView?.setAlpha(0.0)
    }

    override fun showAd() {
        adView?.setAlpha(1.0)
    }

    override fun showInterstitialAd() {
        if (gadInterstitial.isReady && uiViewController != null) {
            gadInterstitial.presentFromRootViewController(uiViewController)
        }
    }

    override fun loadHighScore() {
        if ((GPGManager.sharedInstance() as GPGManager).isSignedIn) {
            val gpgLeaderboard = GPGLeaderboard.leaderboardWithId(Leaderboard.Scores.id)
            gpgLeaderboard?.isSocial = false

            gpgLeaderboard.loadScoresWithCompletionHandler { nsArray, nsError ->
                val score = gpgLeaderboard.localPlayerScore()
                if (gpgLeaderboard != null && score != null) {
                    val nScore = score.scoreValue()
                    Log.d("Retrieved score: " + nScore)
                    if (nScore > 0 && nScore != GameManager.score) {
                        GameManager.onlineScore = nScore
                    }
                }
            }
        }
    }
}
