package anmolsethi.dev.unity_ads_mediation.interstitialAd;

import com.unity3d.mediation.IInterstitialAdLoadListener;
import com.unity3d.mediation.InterstitialAd;
import com.unity3d.mediation.errors.LoadError;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;

import anmolsethi.dev.unity_ads_mediation.UnityAdsConstants;

public class InterstitialAdLoadListener implements IInterstitialAdLoadListener {
    private final Map<String, MethodChannel> placementChannels = new HashMap<>();
    private final BinaryMessenger messenger;
    private final MethodChannel defaultChannel;

    public InterstitialAdLoadListener(MethodChannel defaultChannel, BinaryMessenger messenger) {
        this.defaultChannel = defaultChannel;
        this.messenger = messenger;
    }

    private MethodChannel findChannel(String placementId) {
        if (placementChannels.containsKey(placementId)) {
            return placementChannels.get(placementId);
        }
        MethodChannel methodChannel = new MethodChannel(messenger,
                UnityAdsConstants.INTERSTITIAL_AD_CHANNEL + "_" + placementId);
        placementChannels.put(placementId, methodChannel);
        return methodChannel;
    }

    private void invokeMethod(String methodName, Map<String, Object> arguments, String placementId) {
        defaultChannel.invokeMethod(methodName, arguments);
        if (placementId != null) {
            findChannel(placementId).invokeMethod(methodName, arguments);
        }
    }

    @Override
    public void onInterstitialLoaded(InterstitialAd interstitialAd) {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, interstitialAd.getAdUnitId());

        invokeMethod(UnityAdsConstants.INTERSTITIAL_AD_LOADED, arguments, interstitialAd.getAdUnitId());
    }

    @Override
    public void onInterstitialFailedLoad(InterstitialAd interstitialAd, LoadError loadError, String s) {
        Map<String, Object> arguments = new HashMap<>();

        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, interstitialAd.getAdUnitId());
        if (loadError != null)
            arguments.put("errorCode", loadError.toString());
        if (s != null)
            arguments.put("errorMessage", s);

        invokeMethod(UnityAdsConstants.INTERSTITIAL_AD_FAILED, arguments, interstitialAd.getAdUnitId());
    }
}