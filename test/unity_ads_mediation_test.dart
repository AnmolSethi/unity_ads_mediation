import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:unity_ads_mediation/unity_ads_mediation.dart';

void main() {
  const MethodChannel channel = MethodChannel('unity_ads_mediation');
  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async => true);
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('initialize', () async {
    expect(await UnityAdsMediation.initialize(gameId: 'GAME_ID'), true);
  });
}
