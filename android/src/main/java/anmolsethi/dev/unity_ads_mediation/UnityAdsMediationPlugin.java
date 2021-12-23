package anmolsethi.dev.unity_ads_mediation;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.unity3d.mediation.IInitializationListener;
import com.unity3d.mediation.InitializationConfiguration;
import com.unity3d.mediation.InitializationState;
import com.unity3d.mediation.UnityMediation;
import com.unity3d.mediation.errors.SdkInitializationError;

import java.util.HashMap;
import java.util.Map;

import anmolsethi.dev.unity_ads_mediation.interstitialAd.UnityInterstitialAd;
import anmolsethi.dev.unity_ads_mediation.rewardedAd.UnityRewardedAd;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * UnityAdsMediationPlugin
 */
public class UnityAdsMediationPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {

  private static final String TAG = "UnityAdsMediationPlugin";

  private MethodChannel channel;
  private Activity activity;
  private BinaryMessenger binaryMessenger;

  private UnityRewardedAd unityRewardedAd;
  private UnityInterstitialAd unityInterstitialAd;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(),
        UnityAdsConstants.MAIN_CHANNEL);
    channel.setMethodCallHandler(this);
    binaryMessenger = flutterPluginBinding.getBinaryMessenger();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    switch (call.method) {
      case UnityAdsConstants.INIT_METHOD:
        result.success(initialize((Map<String, ?>) call.arguments));
        break;
      case UnityAdsConstants.loadRewardedAd:
        unityRewardedAd = new UnityRewardedAd(binaryMessenger, channel, activity);
        result.success(unityRewardedAd.loadAd((Map<String, ?>) call.arguments));
        break;
      case UnityAdsConstants.showRewardedAd:
        result.success(unityRewardedAd.showAd());
        break;
      // Interstitial Ad
      case UnityAdsConstants.loadInterstitialAd:
        unityInterstitialAd = new UnityInterstitialAd(binaryMessenger, channel, activity);
        result.success(unityInterstitialAd.loadAd((Map<String, ?>) call.arguments));
        break;
      case UnityAdsConstants.showInterstitialAd:
        result.success(unityInterstitialAd.showAd());
        break;
      default:
        result.notImplemented();
    }
  }

  private boolean initialize(Map<?, ?> args) {
    String gameId = (String) args.get(UnityAdsConstants.GAME_ID_PARAMETER);

    InitializationConfiguration configuration = InitializationConfiguration.builder()
        .setGameId(gameId)
        .setInitializationListener(new IInitializationListener() {
          @Override
          public void onInitializationComplete() {
            Log.i(TAG, "Unity Mediation is successfully initialized.");

            Map<String, Object> arguments = new HashMap<>();
            arguments.put("isInitialized", true);
            channel.invokeMethod(UnityAdsConstants.AD_INITIALIZED, arguments);
          }

          @Override
          public void onInitializationFailed(SdkInitializationError errorCode, String msg) {
            Log.e(TAG, "Unity Mediation Failed to Initialize: " + msg);

            Map<String, Object> arguments = new HashMap<>();
            arguments.put("errorCode", errorCode.toString());
            arguments.put("errorMessage", msg);
            channel.invokeMethod(UnityAdsConstants.AD_INITIALIZED_ERROR, arguments);
          }
        }).build();

    UnityMediation.initialize(configuration);
    return UnityMediation.getInitializationState() == InitializationState.INITIALIZED;
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
  }

  @Override
  public void onDetachedFromActivity() {
  }
}
