package anmolsethi.dev.unity_ads_mediation;

public interface UnityAdsConstants {
    String MAIN_CHANNEL = "unity_ads_mediation";

    String REWARDED_AD_CHANNEL = MAIN_CHANNEL + "/videoAd";
    String INTERSTITIAL_AD_CHANNEL = MAIN_CHANNEL + "/fullScreenAd";

    String GAME_ID_PARAMETER = "gameId";

    String PLACEMENT_ID_PARAMETER = "placementId";

    String INIT_METHOD = "init";
    String loadRewardedAd = "loadRewardedAd";
    String showRewardedAd = "showRewardedAd";

    String loadInterstitialAd = "loadInterstitialAd";
    String showInterstitialAd = "showInterstitialAd";

    String REWARDED_AD_LOADED = "rewardedAdLoaded";
    String REWARDED_AD_FAILED = "rewardedAdFailed";

    String INTERSTITIAL_AD_LOADED = "rewardedAdLoaded";
    String INTERSTITIAL_AD_FAILED = "rewardedAdFailed";

    String AD_SHOWED = "showed";
    String AD_CLICKED = "clicked";
    String AD_CLOSED = "closed";
    String AD_ERROR = "failed";
    String AD_REWARDED = "rewarded";

    String AD_INITIALIZED = "adInitialized";
    String AD_NOT_INITIALIZED = "adNotInitialized";
    String AD_INITIALIZED_ERROR = "adInitializedError";
}