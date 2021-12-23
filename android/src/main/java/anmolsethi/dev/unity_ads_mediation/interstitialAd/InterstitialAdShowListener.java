package anmolsethi.dev.unity_ads_mediation.interstitialAd;

import com.unity3d.mediation.IInterstitialAdShowListener;
import com.unity3d.mediation.InterstitialAd;
import com.unity3d.mediation.errors.ShowError;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;

import anmolsethi.dev.unity_ads_mediation.UnityAdsConstants;

public class InterstitialAdShowListener implements IInterstitialAdShowListener {
    private final Map<String, MethodChannel> placementChannels = new HashMap<>();
    private final BinaryMessenger messenger;
    private final MethodChannel defaultChannel;

    public InterstitialAdShowListener(MethodChannel defaultChannel, BinaryMessenger messenger) {
        this.defaultChannel = defaultChannel;
        this.messenger = messenger;
    }

    @Override
    public void onInterstitialShowed(InterstitialAd ad) {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, ad.getAdUnitId());

        invokeMethod(UnityAdsConstants.AD_SHOWED, arguments, ad.getAdUnitId());
    }

    @Override
    public void onInterstitialClicked(InterstitialAd ad) {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, ad.getAdUnitId());

        invokeMethod(UnityAdsConstants.AD_CLICKED, arguments, ad.getAdUnitId());
    }

    @Override
    public void onInterstitialClosed(InterstitialAd ad) {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, ad.getAdUnitId());

        invokeMethod(UnityAdsConstants.AD_CLOSED, arguments, ad.getAdUnitId());
    }

    @Override
    public void onInterstitialFailedShow(InterstitialAd ad, ShowError showError, String s) {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, ad.getAdUnitId());
        arguments.put("error", showError.toString());

        invokeMethod(UnityAdsConstants.AD_ERROR, arguments, ad.getAdUnitId());
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
}