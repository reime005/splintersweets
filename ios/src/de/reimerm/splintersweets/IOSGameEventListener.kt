package de.reimerm.splintersweets

import apple.uikit.UIViewController
import de.reimerm.splintersweets.leaderboards.Leaderboard
import de.reimerm.splintersweets.utils.GameEventListener
import de.reimerm.splintersweets.utils.GameManager
import org.moe.googlemobileads.GADBannerView
import org.moe.googlemobileads.GADInterstitial

class IOSGameEventListener(private val adView: GADBannerView?, private val gadInterstitial: GADInterstitial, private val uiViewController: UIViewController?) : GameEventListener {

    override fun submitScore() {
    }

    override fun login() {
    }

    override fun displayLeaderBoard() {
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
    }
}
