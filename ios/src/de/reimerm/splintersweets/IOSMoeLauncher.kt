package de.reimerm.splintersweets

import apple.coregraphics.struct.CGPoint
import apple.coregraphics.struct.CGRect
import apple.foundation.*
import apple.uikit.UIApplication
import apple.uikit.UIScreen
import apple.uikit.UIViewController
import apple.uikit.c.UIKit
import apple.uikit.protocol.UIApplicationDelegate
import com.badlogic.gdx.backends.iosmoe.IOSApplication
import com.badlogic.gdx.backends.iosmoe.IOSApplicationConfiguration
import de.reimerm.splintersweets.main.MainGame
import de.reimerm.splintersweets.utils.GameManager
import de.reimerm.splintersweets.utils.GameSettings
import org.moe.googlemobileads.GADBannerView
import org.moe.googlemobileads.GADInterstitial
import org.moe.googlemobileads.GADMobileAds
import org.moe.googlemobileads.GADRequest
import org.moe.googlemobileads.c.GoogleMobileAds
import org.moe.natj.general.Pointer
import org.moe.natj.general.ann.Mapped
import org.moe.natj.objc.ann.ObjCClassName
import org.moe.natj.objc.ann.Selector
import org.moe.natj.objc.map.ObjCObjectMapper

@ObjCClassName("IOSMoeLauncher")
class IOSMoeLauncher protected constructor(peer: Pointer) : IOSApplication.Delegate(peer), UIApplicationDelegate {
    //
    private var gdxApp: IOSApplication? = null
    private var uiViewController: UIViewController? = null
    private var adView: GADBannerView? = null

    override fun createApplication(): IOSApplication {
        val config = IOSApplicationConfiguration()
        config.useAccelerometer = false
        gdxApp = IOSApplication(MainGame, config)
        return gdxApp!!
    }

    override fun applicationDidBecomeActive(application: UIApplication?) {
        if (gdxApp != null) {
            uiViewController = gdxApp!!.uiViewController
        }

        GADMobileAds.configureWithApplicationID(GameSettings.ADMOB_APP_ID)

        adView = GADBannerView.alloc().initWithAdSize(GoogleMobileAds.kGADAdSizeBanner())
        adView?.setAdUnitID(GameSettings.ADMOB_BANNER_ID)

        val cgRect = CGRect()
        cgRect.setSize(GoogleMobileAds.kGADAdSizeBanner().size())

        adView?.setFrame(cgRect)
        adView?.setRootViewController(uiViewController)

        val request = GADRequest.request()

        request.setTestDevices(NSArray.arrayWithObject(GoogleMobileAds.kGADSimulatorID()))

        adView?.loadRequest(request)

        adView?.setCenter(CGPoint(UIScreen.mainScreen().bounds().size().width() * 0.5f, 20.0))

        gdxApp?.uiWindow?.addSubview(adView)

        val gadInterstitial = GADInterstitial.alloc().initWithAdUnitID(GameSettings.ADMOB_INTERSTITIAL_ID)
        gadInterstitial.loadRequest(request)

        GameManager.listener = IOSGameEventListener(adView, gadInterstitial, uiViewController)

        super<IOSApplication.Delegate>.applicationDidBecomeActive(application)
    }

    companion object {

        @Selector("alloc")
        @JvmStatic external fun alloc(): IOSMoeLauncher

        @JvmStatic fun main(argv: Array<String>) {
            UIKit.UIApplicationMain(0, null, null, IOSMoeLauncher::class.java.simpleName)
        }
    }
}
