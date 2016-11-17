package de.reimerm.splintersweets

import apple.foundation.NSArray
import apple.gamekit.GKLeaderboard
import apple.gamekit.GKLocalPlayer
import apple.gamekit.GKScore
import apple.gamekit.enums.GKLeaderboardPlayerScope
import apple.gamekit.enums.GKLeaderboardTimeScope
import apple.uikit.UIAlertView
import apple.uikit.UIViewController
import com.badlogic.gdx.backends.iosmoe.IOSApplication
import de.reimerm.splintersweets.main.MainGame
import de.reimerm.splintersweets.screens.HighscoreScreen
import de.reimerm.splintersweets.utils.GameEventListener
import de.reimerm.splintersweets.utils.GameManager
import org.moe.googlemobileads.GADBannerView
import org.moe.googlemobileads.GADInterstitial
import java.util.*

class IOSGameEventListener(private val adView: GADBannerView?, private val gadInterstitial: GADInterstitial, private val uiViewController: UIViewController?, private val iosApplication: IOSApplication) : GameEventListener {

    private var loading = false

    companion object {
        init {
            try {
                Class.forName(GKScore::class.java.name)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    override fun submitScore() {
        if (!isLoggedIn()) {
            return
        }

        var gkScore = GKScore.alloc()
        gkScore = gkScore.initWithLeaderboardIdentifier("de.reimerm.splintersweets")
        gkScore.setValue(GameManager.score)

        val nsArray = NSArray.arrayWithObject(gkScore) as NSArray<GKScore>

        GKScore.reportScoresWithCompletionHandler(nsArray) { nsError ->
            if (nsError != null) {
                Log.d("submitted score not successfully")
            } else {
                Log.d("submitted score successfully")
            }
        }
    }

    override fun login() {
        if (isLoggedIn()) {
            return
        }

        val localPlayer = GKLocalPlayer.localPlayer()
        localPlayer.setAuthenticateHandler { viewController, nsError ->
            if (viewController != null) {
                iosApplication.uiWindow.rootViewController().showDetailViewControllerSender(viewController, null)
            } else if (localPlayer.isAuthenticated) {
                localPlayer.generateIdentityVerificationSignatureWithCompletionHandler { publicKeyUrl, signature, salt, timestamp, error ->
                    Log.d("successfully logged into gamecenter")
                    loadHighScore()
                }
            } else { // canceled by user or GameCenter is disabled
                val alert = UIAlertView.alloc().init()
                alert.setTitle("Info")
                alert.setMessage("Game Center account is required")
                alert.addButtonWithTitle("Ok")
                alert.show()
            }
        }
    }

    override fun displayLeaderBoard() {
        if (!isLoggedIn()) {
            val alert = UIAlertView.alloc().init()
            alert.setTitle("Info")
            alert.setMessage("In order to see the global High Score, please login to GameCenter (Settings -> GameCenter)")
            alert.addButtonWithTitle("Ok")
            alert.show()
            return
        }

        hideAd()

        loadLeaderBoards()
    }

    private fun loadLeaderBoards() {
        if (!isLoggedIn()) {
            Log.d("not logged in game center")
            return
        }

        if (loading) {
            return
        }

        GKLeaderboard.loadLeaderboardsWithCompletionHandler { leaderboards, nsError ->
            loading = true

            if (nsError != null) {
                Log.d("Error on loading leaderboards: %s", nsError.toString())
            }

            if (leaderboards != null && leaderboards.size > 0) {
                leaderboards[0].setPlayerScope(GKLeaderboardPlayerScope.Global)
                leaderboards[0].setTimeScope(GKLeaderboardTimeScope.AllTime)
                //                    arg0.get(0).setRange(new NSRange(1, 30));

                leaderboards[0].loadScoresWithCompletionHandler { scores, nsError ->
                    if (nsError == null && scores != null) {
                        val dataMap = LinkedHashMap<String, String>()
                        for (i in scores.indices) {
                            dataMap.put(scores[i].player().alias(), scores[i].value().toString())
                        }
                        Log.d("display leaderboard")
                        if (MainGame.screen.javaClass == HighscoreScreen::class.java) {
                            MainGame.screen = HighscoreScreen(dataMap, true)
                        } else {
                            Log.d("User left highscore screen")
                        }
                    } else {
                        Log.d("display leaderboard error in loading scores")
                    }
                }
            }

            loading = false
        }
    }

    override fun hideAd() {
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
        if (!isLoggedIn()) {
            return
        }

        GKLeaderboard.loadLeaderboardsWithCompletionHandler(GKLeaderboard.Block_loadLeaderboardsWithCompletionHandler { leaderboards, nsError ->
            if (nsError != null) {
                Log.d("Error on loading leaderboards: %s", nsError.toString())
            }

            if (leaderboards != null && leaderboards.size > 0) {
                leaderboards[0].setPlayerScope(GKLeaderboardPlayerScope.Global)
                leaderboards[0].setTimeScope(GKLeaderboardTimeScope.AllTime)
                //                    arg0.get(0).setRange(new NSRange(1, 30));

                leaderboards[0].loadScoresWithCompletionHandler { scores, nsError ->
                    if (nsError == null && scores != null) {

                        var value: Long = 0
                        val dataMap = LinkedHashMap<String, String>()
                        for (i in scores.indices) {
                            dataMap.put(scores[i].player().alias(), scores[i].value().toString())

                            if (scores[i].player().alias() == GKLocalPlayer.localPlayer().alias()) {
                                try {
                                    value = scores[i].value()
                                } catch (e: NumberFormatException) {
                                    e.printStackTrace()
                                }

                            }
                        }
                        //                                GameManager.getInstance().setScoreData(dataMap);

                        if (value > 0 && value != GameManager.score) {
                            GameManager.onlineScore = value
                        }
                    }
                }
            }
        })
    }

    private fun isLoggedIn(): Boolean {
        return GKLocalPlayer.localPlayer().isAuthenticated
    }
}
