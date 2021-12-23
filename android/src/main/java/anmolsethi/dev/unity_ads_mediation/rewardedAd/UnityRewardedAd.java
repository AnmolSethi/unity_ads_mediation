package anmolsethi.dev.unity_ads_mediation.rewardedAd;

import android.app.Activity;
import android.util.Log;

import com.unity3d.mediation.AdState;
import com.unity3d.mediation.RewardedAd;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;

import java.util.Map;

import anmolsethi.dev.unity_ads_mediation.UnityAdsConstants;

public class UnityRewardedAd {
    private static final String TAG = "UnityRewardedAd";
    private RewardedAd rewardedAd;
    private final BinaryMessenger messenger;
    private final MethodChannel defaultChannel;
    private final Activity activity;

    public UnityRewardedAd(BinaryMessenger messenger, MethodChannel channel, Activity activity) {
        this.messenger = messenger;
        this.defaultChannel = channel;
        this.activity = activity;
    }

    public boolean loadAd(Map<?, ?> args) {
        final String placementId = (String) args.get(UnityAdsConstants.PLACEMENT_ID_PARAMETER);
        if (placementId == null) {
            return false;
        }

        Log.i(TAG, "Loading UnityRewardedAd with placement id: " + placementId);
        rewardedAd = new RewardedAd(activity, placementId);
        rewardedAd.load(new RewardedAdLoadListener(defaultChannel, messenger));
        return rewardedAd.getAdState() == AdState.LOADED;
    }

    public boolean showAd() {
        Log.i(TAG, "Showing UnityRewardedAd");
        rewardedAd.show(new RewardedAdShowListener(defaultChannel, messenger));
        return true;
    }
}
