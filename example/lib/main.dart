import 'package:flutter/material.dart';
import 'package:unity_ads_mediation/unity_ads_mediation.dart';

void main() {
  runApp(const UnityAdsExampleApp());
}

class UnityAdsExampleApp extends StatelessWidget {
  const UnityAdsExampleApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Unity Ads Mediation Example',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Unity Ads Mediation Example'),
        ),
        body: const SafeArea(
          child: UnityAdsExample(),
        ),
      ),
    );
  }
}

class UnityAdsExample extends StatefulWidget {
  const UnityAdsExample({Key? key}) : super(key: key);

  @override
  _UnityAdsExampleState createState() => _UnityAdsExampleState();
}

class _UnityAdsExampleState extends State<UnityAdsExample> {
  @override
  void initState() {
    super.initState();

    UnityAdsMediation.initialize(
      gameId: AdManager.gameId,
      listener: (state, args) {
        // print('Init Listener: $state => $args')
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: double.infinity,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          ElevatedButton(
            onPressed: () {
              UnityAdsMediation.loadRewardedAd(
                placementId: AdManager.rewardedVideoAdPlacementId,
                listener: (state, args) {
                  // print('Load Listener: $state => $args');
                },
              );
            },
            child: const Text('Load Rewarded Video'),
          ),
          ElevatedButton(
            onPressed: () {
              UnityAdsMediation.showRewardedAd(
                placementId: AdManager.rewardedVideoAdPlacementId,
                listener: (state, args) {
                  // print('Rewarded Video Listener: $state => $args')
                },
              );
            },
            child: const Text('Show Interstitial Video'),
          ),
          ElevatedButton(
            onPressed: () {
              UnityAdsMediation.loadInterstitialAd(
                placementId: AdManager.interstitailAdPlacementId,
                listener: (state, args) {
                  // print('Load Listener: $state => $args');
                },
              );
            },
            child: const Text('Load Interstitial Video'),
          ),
          ElevatedButton(
            onPressed: () {
              UnityAdsMediation.showInterstitailAd(
                placementId: AdManager.interstitailAdPlacementId,
                listener: (state, args) {
                  // print('Interstitial Video Listener: $state => $args')
                },
              );
            },
            child: const Text('Show Interstitial Video'),
          ),
        ],
      ),
    );
  }
}

class AdManager {
  static String get gameId => 'GAME_ID';

  static String get rewardedVideoAdPlacementId => 'Rewarded_Android';
  static String get interstitailAdPlacementId => 'Interstitial_Android';
}
