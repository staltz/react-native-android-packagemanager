
package com.reactlibrary;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.List;

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

      PackageInfoMapping info = new PackageInfoMapping(pi, pm);
      WritableMap map = info.asWritableMap();

      promise.resolve(map);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      promise.reject(null, ex.getMessage());
    }
  }

  @ReactMethod
  public void getInstalledPackages(Promise promise) {
    try {
      WritableArray array = Arguments.createArray();

      PackageManager pm = this.reactContext.getPackageManager();
      List<PackageInfo> packages = pm.getInstalledPackages(0);
      for (PackageInfo pi : packages)
      {
        PackageInfoMapping info = new PackageInfoMapping(pi, pm);
        WritableMap map = info.asWritableMap();

        array.pushMap(map);
      }

      promise.resolve(array);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      promise.reject(null, ex.getMessage());
    }
  }
}
