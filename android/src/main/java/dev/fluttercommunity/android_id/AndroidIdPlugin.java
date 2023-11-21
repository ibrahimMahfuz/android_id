package dev.fluttercommunity.android_id;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.provider.Settings;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import android.annotation.SuppressLint;
import android.provider.Settings;

public class AndroidIdPlugin implements FlutterPlugin, MethodCallHandler {
  private MethodChannel channel;
  private ContentResolver contentResolver;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "android_id");
    channel.setMethodCallHandler(this);
    contentResolver = flutterPluginBinding.getApplicationContext().getContentResolver();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getAndroidId")) {
      String androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
      result.success(androidId);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  // Fetch the Android ID while suppressing lint warning about hardware IDs
  @SuppressLint("HardwareIds")
  private fun getAndroidId(): String? {
    return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
  }
}