package anmolsethi.dev.unity_ads_mediation.rewardedAd;

import com.unity3d.mediation.IRewardedAdLoadListener;
import com.unity3d.mediation.RewardedAd;
import com.unity3d.mediation.errors.LoadError;

import java.util.HashMap;
import java.util.Map;

import anmolsethi.dev.unity_ads_mediation.UnityAdsConstants;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;

public class RewardedAdLoadListener implements IRewardedAdLoadListener {
    private final Map<String, MethodChannel> placementChannels = new HashMap<>();
    private final BinaryMessenger messenger;
    private final MethodChannel defaultChannel;

    public RewardedAdLoadListener(MethodChannel defaultChannel, BinaryMessenger messenger) {
        this.defaultChannel = defaultChannel;
        this.messenger = messenger;
    }

    @Override
    public void onRewardedLoaded(RewardedAd rewardedAd) {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, rewardedAd.getAdUnitId());

        invokeMethod(UnityAdsConstants.REWARDED_AD_LOADED, arguments, rewardedAd.getAdUnitId());
    }

    @Override
    public void onRewardedFailedLoad(RewardedAd rewardedAd, LoadError loadError, String s) {
        Map<String, Object> arguments = new HashMap<>();

        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, rewardedAd.getAdUnitId());

        if (loadError != null) {
            arguments.put("errorCode", loadError.toString());
        }

        if (s != null) {
            arguments.put("errorMessage", s);
        }

        invokeMethod(UnityAdsConstants.REWARDED_AD_FAILED, arguments, rewardedAd.getAdUnitId());
    }

    private MethodChannel findChannel(String placementId) {
        if (placementChannels.containsKey(placementId)) {
            return placementChannels.get(placementId);
        }
        MethodChannel methodChannel = new MethodChannel(messenger,
                UnityAdsConstants.REWARDED_AD_CHANNEL + "_" + placementId);
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
