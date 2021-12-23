import 'dart:async';

import 'package:flutter/services.dart';
import 'src/constants.dart';

class UnityAdsMediation {
  static const MethodChannel _channel = MethodChannel(mainChannel);

  /// Initialize Unity Ads
  ///
  /// * [gameId] - identifier from Project Settings in Unity Dashboard.
  static Future<bool?> initialize({
    required String gameId,
    Function(UnityInitializationState initializationState,
            Map<String, dynamic> result)?
        listener,
  }) async {
    try {
      if (listener != null) {
        _channel.setMethodCallHandler((call) => _initMetodCall(call, listener));
      }
      return await _channel.invokeMethod(initMethod, {
        gameIdParameter: gameId,
      });
    } on PlatformException {
      return false;
    }
  }

  static final Map<String, MethodChannel> _channels = {};

  /// Load Rewarded Ad
  ///
  /// [placementId] placement identifier, as defined in Unity Ads Dashboard
  /// [adState] - current state of the ad
  /// [listener] - callback for ad state changes
  static Future<bool?> loadRewardedAd({
    required String placementId,
    Function(UnityAdsState adState, dynamic result)? listener,
  }) async {
    try {
      if (listener != null) {
        _channels
            .putIfAbsent(placementId,
                () => MethodChannel('${videoAdChannel}_$placementId'))
            .setMethodCallHandler((call) => _loadMethodCall(call, listener));
      }

      return await _channel.invokeMethod(loadRewardedAdMethod, {
        placementIdParameter: placementId,
      });
    } on PlatformException {
      return false;
    }
  }

  /// Show Rewarded Ad, if ready.
  ///
  /// [placementId] placement identifier, as defined in Unity Ads Dashboard
  /// [UnityAdsState] adState - current state of the ad
  /// [listener] - callback for ad state changes
  static Future<bool?> showRewardedAd({
    required String placementId,
    Function(UnityAdsState adState, Map<String, dynamic> result)? listener,
  }) async {
    try {
      if (listener != null) {
        _channels
            .putIfAbsent(placementId,
                () => MethodChannel('${videoAdChannel}_$placementId'))
            .setMethodCallHandler((call) => _showMethodCall(call, listener));
      }

      final result = await _channel.invokeMethod(showRewardedAdMethod, {
        placementIdParameter: placementId,
      });
      return result;
    } on PlatformException {
      return false;
    }
  }

  /// Load Interstitial Ad
  ///
  /// [placementId] placement identifier, as defined in Unity Ads Dashboard
  /// [adState] - current state of the ad
  /// [listener] - callback for ad state changes
  static Future<bool?> loadInterstitialAd({
    required String placementId,
    Function(UnityAdsState adState, dynamic result)? listener,
  }) async {
    try {
      if (listener != null) {
        _channels
            .putIfAbsent(placementId,
                () => MethodChannel('${fullAdChannel}_$placementId'))
            .setMethodCallHandler((call) => _loadMethodCall(call, listener));
      }

      return await _channel.invokeMethod(loadInterstitialAdMethod, {
        placementIdParameter: placementId,
      });
    } on PlatformException {
      return false;
    }
  }

  /// Show Interstitial Ad, if ready.
  ///
  /// [placementId] placement identifier, as defined in Unity Ads Dashboard
  /// [UnityAdsState] adState - current state of the ad
  /// [listener] - callback for ad state changes
  static Future<bool?> showInterstitailAd({
    required String placementId,
    Function(UnityAdsState adState, Map<String, dynamic> result)? listener,
  }) async {
    try {
      if (listener != null) {
        _channels
            .putIfAbsent(placementId,
                () => MethodChannel('${fullAdChannel}_$placementId'))
            .setMethodCallHandler((call) => _showMethodCall(call, listener));
      }

      return await _channel.invokeMethod(showInterstitialAdMethod, {
        placementIdParameter: placementId,
      });
    } on PlatformException {
      return false;
    }
  }

  static Future<dynamic> _initMetodCall(
      MethodCall call,
      Function(UnityInitializationState adState, Map<String, dynamic>)
          listener) {
    switch (call.method) {
      case adIntialized:
        listener(UnityInitializationState.initialized, call.arguments);
        break;
      case adIntializedError:
        listener(UnityInitializationState.error, call.arguments);
        break;
      case adNotIntialized:
      default:
        listener(UnityInitializationState.notInitialized, call.arguments);
        break;
    }

    return Future.value(true);
  }

  static Future<dynamic> _loadMethodCall(MethodCall call,
      Function(UnityAdsState adState, Map<String, dynamic>) listener) {
    switch (call.method) {
      case adLoaded:
        listener(UnityAdsState.loaded, call.arguments);
        break;
      case adFailed:
        listener(UnityAdsState.failed, call.arguments);
        break;
    }
    return Future.value(true);
  }

  static Future<dynamic> _showMethodCall(MethodCall call,
      Function(UnityAdsState adState, Map<String, dynamic> result) listener) {
    switch (call.method) {
      case adShowed:
        listener(UnityAdsState.showed, call.arguments);
        break;
      case adClicked:
        listener(UnityAdsState.clicked, call.arguments);
        break;
      case adError:
        listener(UnityAdsState.error, call.arguments);
        break;
      case adClosed:
        listener(UnityAdsState.closed, call.arguments);
        break;
      case adRewarded:
        listener(UnityAdsState.rewarded, call.arguments);
        break;
    }
    return Future.value(true);
  }
}

/// Ad State of the Unity Ads
enum UnityAdsState {
  // The ad is ready to be displayed.
  ready,

  // The ad is having some errors.
  error,

  // The ad is loaded.
  loaded,

  // The ad isf failed to load.
  failed,

  // The ad is being showed.
  showed,

  // The ad is being closed.
  closed,

  // The ad is being clicked.
  clicked,

  // User is rewarded.
  rewarded
}

/// Intialization State of the Unity Ads SDK
enum UnityInitializationState { initialized, notInitialized, error }
