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
import org.moe.googleplus.protocol.GPPSignInDelegate
import org.moe.googlesignin.GIDGoogleUser
import org.moe.googlesignin.GIDSignIn
import org.moe.googlesignin.protocol.GIDSignInDelegate
import org.moe.googlesignin.protocol.GIDSignInUIDelegate
import org.moe.gpg.GPGManager
import org.moe.gpg.enums.GPGToastPlacement
import org.moe.gpg.protocol.GPGStatusDelegate
import org.moe.natj.general.Pointer
import org.moe.natj.general.ann.Mapped
import org.moe.natj.objc.ann.ObjCClassName
import org.moe.natj.objc.ann.Selector
import org.moe.natj.objc.map.ObjCObjectMapper

@ObjCClassName("IOSMoeLauncher")
class IOSMoeLauncher protected constructor(peer: Pointer) : IOSApplication.Delegate(peer), GPPSignInDelegate, UIApplicationDelegate, GPGStatusDelegate, GIDSignInUIDelegate, GIDSignInDelegate {
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

        (GPGManager.sharedInstance() as GPGManager).setWelcomeBackToastPlacement(GPGToastPlacement.Center)
        (GPGManager.sharedInstance() as GPGManager).setAchievementUnlockedToastPlacement(GPGToastPlacement.Center)

        val scopes = NSArray.arrayWithObject(NSString.stringWithString("https://www.googleapis.com/auth/games"))

        GIDSignIn.sharedInstance().setDelegate(this)
        GIDSignIn.sharedInstance().setUiDelegate(this)

        GIDSignIn.sharedInstance().setScopes(scopes)

        GIDSignIn.sharedInstance().setClientID(GameSettings.CLIENT_ID)

        (GPGManager.sharedInstance() as GPGManager).setStatusDelegate(this)

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

        (GPGManager.sharedInstance() as GPGManager).signInWithClientIDSilently(GameSettings.CLIENT_ID, true)

        super<IOSApplication.Delegate>.applicationDidBecomeActive(application)
    }

    override fun didDisconnectWithError(nsError: NSError?) {
        Log.d("didDisconnectWithError")
    }

    override fun applicationDidFinishLaunchingWithOptions(application: UIApplication?, launchOptions: NSDictionary<*, *>?): Boolean {
        return super<IOSApplication.Delegate>.applicationDidFinishLaunchingWithOptions(application, launchOptions)
    }

    override fun didFinishGamesSignInWithError(nsError: NSError?) {
        Log.d("didFinishGamesSignInWithError")
    }

    override fun didFinishGamesSignOutWithError(nsError: NSError?) {
        Log.d("didFinishGamesSignOutWithError")
    }

    override fun didFinishGoogleAuthWithError(nsError: NSError?) {
        Log.d("didFinishGoogleAuthWithError")
    }

    override fun shouldReauthenticateWithError(nsError: NSError?): Boolean {
        Log.d("shouldReauthenticateWithError")
        return false
    }

    override fun willReauthenticate(nsError: NSError?) {
        Log.d("willReauthenticate")
    }

    override fun applicationOpenURLOptions(app: UIApplication?, url: NSURL?, options: NSDictionary<String, *>?): Boolean {
        Log.d("applicationOpenURLOptions")
        return GIDSignIn.sharedInstance().handleURLSourceApplicationAnnotation(url, options!!["UIApplicationOpenURLOptionsSourceApplicationKey"].toString(), options["UIApplicationOpenURLOptionsAnnotationKey"])
    }

    override fun applicationOpenURLSourceApplicationAnnotation(application: UIApplication?, url: NSURL?, sourceApplication: String?, @Mapped(ObjCObjectMapper::class) annotation: Any?): Boolean {
        Log.d("applicationOpenURLSourceApplicationAnnotation")
        return GIDSignIn.sharedInstance().handleURLSourceApplicationAnnotation(url, sourceApplication, annotation)
    }

    override fun signInDismissViewController(gidSignIn: GIDSignIn?, uiViewController: UIViewController?) {
        Log.d("signInDismissViewController")
    }

    override fun signInPresentViewController(gidSignIn: GIDSignIn?, uiViewController: UIViewController?) {
        Log.d("signInPresentViewController()")
        if (this.uiViewController != null) {
            this.uiViewController!!.showDetailViewControllerSender(uiViewController, gidSignIn)
        }
    }

    override fun signInWillDispatchError(gidSignIn: GIDSignIn?, nsError: NSError?) {
        Log.d("signInWillDispatchError")
    }

    override fun signInDidDisconnectWithUserWithError(gidSignIn: GIDSignIn?, gidGoogleUser: GIDGoogleUser?, nsError: NSError?) {
        Log.d("signInDidDisconnectWithUserWithError")
    }

    override fun signInDidSignInForUserWithError(gidSignIn: GIDSignIn, gidGoogleUser: GIDGoogleUser, nsError: NSError) {
        Log.d("signInDidSignInForUserWithError")
    }

    companion object {

        @Selector("alloc")
        @JvmStatic external fun alloc(): IOSMoeLauncher

        @JvmStatic fun main(argv: Array<String>) {
            UIKit.UIApplicationMain(0, null, null, IOSMoeLauncher::class.java.simpleName)
        }
    }
}
