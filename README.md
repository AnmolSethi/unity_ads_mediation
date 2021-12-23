# Unity Mediation Ads

[![Pub](https://img.shields.io/pub/v/unity_ads_mediation.svg)](https://pub.dev/packages/unity_ads_mediation)
[![License](https://img.shields.io/github/license/pavzay/unity_ads_mediation)](https://github.com/pavzay/unity_ads_mediation/blob/master/LICENSE)

[Unity Mediation Ads](https://docs.unity.com/mediation/IntroToMediation) is a plugin for Flutter Applications. This plugin is able to display and Unity Rewarded Ads and Interstitial Ads.

_Note: We are working on the other ad formats as well_

- [Getting Started](#getting-started)
  - [1. Initialization](#1-initialization)
  - [2. Add Adapters](#1-add-adapters)
  - [3. Show Rewarded Ad](#3-show-rewarded-ad)
  - [4. Show Interstitial Ad](#4-show-interstitial-ad)

## Getting Started

### 1. Initialization:

```dart
    UnityAdsMediation.initialize(
      gameId: 'GAME_ID',
      listener: (UnityInitializationState initializationState, Map<String, dynamic> result) {
          if (initializationState == UnityInitializationState.initialized) {
            <!-- This plugin is awesome -->
          } else if(initializationState == UnityInitializationState.error) {
            <!-- Something went wrong -->
          }
      },
    );
```

- Get your GAME_ID from Unity Dashboard.

---

### 2. Add Adapters:

1. Open the build.gradle file in your project.
2. Add the following implementations in the dependencies section of the file, ensuring the dependencies of the adapters you intend to use in your project are declared:

```c
    implementation "com.unity3d.mediation:admob-adapter:0.3.0"
    implementation "com.unity3d.mediation:adcolony-adapter:0.3.0"
    implementation "com.unity3d.mediation:applovin-adapter:0.3.0"
    implementation "com.unity3d.mediation:facebook-adapter:0.3.0"
    implementation "com.unity3d.mediation:ironsource-adapter:0.3.0"
    implementation "com.unity3d.mediation:vungle-adapter:0.3.0"
```

---

### 3. Show Rewarded Ad:

![Rewarded Ad](https://github.com/pavzay/flutter_unity_ads/raw/master/example/images/rewarded.gif "Rewarded Ad")

```dart
UnityAdsMediation.showRewardedAd(
  placementId: 'REWARDED_AD_ID',
  listener: (UnityAdsState adState, Map<String, dynamic> result) {
    if (state == UnityAdState.rewarded) {
      <!-- User has watched a video. User should get a reward! -->
    } else if (state == UnityAdState.error) {
      <!-- Error -->
    }
  },
);
```

### 4. Show Interstitial Ad:

![Interstitial Ad](https://github.com/pavzay/flutter_unity_ads/raw/master/example/images/interstitial.gif "Interstitial Ad")

```dart
UnityAdsMediation.showInterstitialAd(
  placementId: 'INTERSTITIAL_AD_ID',
  listener: (UnityAdsState adState, Map<String, dynamic> result) {
    if (state == UnityAdState.rewarded) {
      <!-- User has watched a video. User should get a reward! -->
    } else if (state == UnityAdState.error) {
      <!-- Error -->
    }
  },
);
```

UnityAdsState:

| State    | Description                                           |
| -------- | ----------------------------------------------------- |
| rewarded | Video played till the end. Use it to reward the user. |
| error    | Some error occurred.                                  |
| showed   | Ad showed to the user                                 |
| closed   | Ad closed by the user                                 |
| clicked  | User clicked the ad.                                  |

---

[![Pub likes](https://badgen.net/pub/likes/unity_ads_mediation)](https://pub.dev/packages/unity_ads_mediation/score)
[![Pub popularity](https://badgen.net/pub/popularity/unity_ads_mediation)](https://pub.dev/packages/unity_ads_mediation/score)
[![Pub points](https://badgen.net/pub/points/unity_ads_mediation)](https://pub.dev/packages/unity_ads_mediation/score)
[![Flutter platform](https://badgen.net/pub/flutter-platform/unity_ads_mediation)](https://pub.dev/packages/unity_ads_mediation)
[![GitHub popularity](https://img.shields.io/github/stars/pavzay/unity_ads_mediation?logo=github&logoColor=white)](https://github.com/pavzay/flutter_unity_ads)
