package anmolsethi.dev.unity_ads_mediation.interstitialAd;

import android.app.Activity;
import android.util.Log;

import com.unity3d.mediation.AdState;
import com.unity3d.mediation.InterstitialAd;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;

import java.util.Map;

import anmolsethi.dev.unity_ads_mediation.UnityAdsConstants;

public class UnityInterstitialAd {
    private static final String TAG = "UnityInterstitialAd";
    private InterstitialAd interstitialAd;
    private final BinaryMessenger messenger;
    private final MethodChannel defaultChannel;
    private final Activity activity;

    public UnityInterstitialAd(BinaryMessenger messenger, MethodChannel channel, Activity activity) {
        this.messenger = messenger;
        this.defaultChannel = channel;
        this.activity = activity;
    }

    public boolean loadAd(Map<?, ?> args) {
        final String placementId = (String) args.get(UnityAdsConstants.PLACEMENT_ID_PARAMETER);
        if (placementId == null) {
            return false;
        }

        Log.i(TAG, "Loading UnityInterstitialAd with placement id: " + placementId);
        interstitialAd = new InterstitialAd(activity, placementId);
        interstitialAd.load(new InterstitialAdLoadListener(defaultChannel, messenger));
        return interstitialAd.getAdState() == AdState.LOADED;
    }

    public boolean showAd() {
        Log.i(TAG, "Showing UnityInterstitialAd");
        interstitialAd.show(new InterstitialAdShowListener(defaultChannel, messenger));
        return true;
    }
}
