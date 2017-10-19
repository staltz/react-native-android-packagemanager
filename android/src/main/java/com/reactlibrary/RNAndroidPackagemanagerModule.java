
package com.reactlibrary;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

public class RNAndroidPackagemanagerModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNAndroidPackagemanagerModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNAndroidPackagemanager";
  }

  @ReactMethod
  public void getPackageInfo(String path, Promise promise) {
    try {
      PackageManager pm = this.reactContext.getPackageManager();
      PackageInfo pi = pm.getPackageArchiveInfo(path, 0);
      ApplicationInfo ai = pi.applicationInfo;
      ai.sourceDir = path;
      ai.publicSourceDir = path;

      String pkg = pi.packageName;
      String label = pm.getApplicationLabel(ai).toString();
      String versionName = pi.versionName;
      int versionCode = pi.versionCode;
      long firstInstallTime = pi.firstInstallTime;
      long lastUpdateTime = pi.lastUpdateTime;

      WritableMap info = Arguments.createMap();
      info.putString("package", pkg);
      info.putString("label", label);
      info.putString("versionName", versionName);
      info.putDouble("versionCode", versionCode);
      info.putDouble("firstInstallTime", firstInstallTime);
      info.putDouble("lastUpdateTime", lastUpdateTime);

      promise.resolve(info);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      promise.reject(null, ex.getMessage());
    }
  }
}