package anmolsethi.dev.unity_ads_mediation.rewardedAd;

import com.unity3d.mediation.IReward;
import com.unity3d.mediation.IRewardedAdShowListener;
import com.unity3d.mediation.RewardedAd;
import com.unity3d.mediation.errors.ShowError;

import java.util.HashMap;
import java.util.Map;

import anmolsethi.dev.unity_ads_mediation.UnityAdsConstants;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;

public class RewardedAdShowListener implements IRewardedAdShowListener {
    private final Map<String, MethodChannel> placementChannels = new HashMap<>();
    private final BinaryMessenger messenger;
    private final MethodChannel defaultChannel;

    public RewardedAdShowListener(MethodChannel defaultChannel, BinaryMessenger messenger) {
        this.defaultChannel = defaultChannel;
        this.messenger = messenger;
    }

    @Override
    public void onRewardedShowed(RewardedAd rewardedAd) {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, rewardedAd.getAdUnitId());

        invokeMethod(UnityAdsConstants.AD_SHOWED, arguments, rewardedAd.getAdUnitId());
    }

    @Override
    public void onRewardedClicked(RewardedAd rewardedAd) {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, rewardedAd.getAdUnitId());

        invokeMethod(UnityAdsConstants.AD_CLICKED, arguments, rewardedAd.getAdUnitId());
    }

    @Override
    public void onRewardedClosed(RewardedAd rewardedAd) {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, rewardedAd.getAdUnitId());

        invokeMethod(UnityAdsConstants.AD_CLOSED, arguments, rewardedAd.getAdUnitId());
    }

    @Override
    public void onRewardedFailedShow(RewardedAd rewardedAd, ShowError showError, String s) {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, rewardedAd.getAdUnitId());
        arguments.put("error", showError.toString());

        invokeMethod(UnityAdsConstants.AD_ERROR, arguments, rewardedAd.getAdUnitId());
    }

    @Override
    public void onUserRewarded(RewardedAd rewardedAd, IReward iReward) {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put(UnityAdsConstants.PLACEMENT_ID_PARAMETER, rewardedAd.getAdUnitId());

        invokeMethod(UnityAdsConstants.AD_REWARDED, arguments, rewardedAd.getAdUnitId());
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
