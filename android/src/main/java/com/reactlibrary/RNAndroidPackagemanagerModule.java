package com.reactlibrary;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.net.Uri;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RNAndroidPackagemanagerModule extends ReactContextBaseJavaModule {

  private final int UNINSTALL_REQUEST_CODE = 1;
  private final String CUSTOMER_DECLINED_KEY = "CUSTOMER_DECLINED";
  private final int CUSTOMER_DECLINED_CODE = Activity.RESULT_CANCELED;
  private final String UNINSTALL_FAILED_KEY = "UNINSTALL_FAILED";
  private final int UNINSTALL_FAILED_CODE = Activity.RESULT_FIRST_USER;

  private final ReactApplicationContext reactContext;
  private Promise mUninstallPromise;
  private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
      super.onActivityResult(requestCode, resultCode, intent);
      if ((requestCode == UNINSTALL_REQUEST_CODE) && (mUninstallPromise != null)) {
        if (resultCode == Activity.RESULT_OK) {
          mUninstallPromise.resolve(true);
        } else if (resultCode == CUSTOMER_DECLINED_CODE) {
          mUninstallPromise.reject(CUSTOMER_DECLINED_KEY, CUSTOMER_DECLINED_KEY);
        } else if (resultCode == UNINSTALL_FAILED_CODE) {
          mUninstallPromise.reject(UNINSTALL_FAILED_KEY, UNINSTALL_FAILED_KEY);
        }
        mUninstallPromise = null;
      }
    }
  };

  public RNAndroidPackagemanagerModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.reactContext.addActivityEventListener(mActivityEventListener);
  }

  @Override
  public String getName() {
    return "RNAndroidPackagemanager";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(CUSTOMER_DECLINED_KEY, CUSTOMER_DECLINED_KEY);
    constants.put(UNINSTALL_FAILED_KEY, UNINSTALL_FAILED_KEY);
    return constants;
  }

  @ReactMethod
  public void getPackageInfo(String path, Promise promise) {
    try {
      PackageManager pm = this.reactContext.getPackageManager();
      PackageInfo pi = pm.getPackageArchiveInfo(path, 0);

      PackageInfoMapping info = new PackageInfoMapping.Builder(pi, pm).withLabel(true).build();
      WritableMap map = info.asWritableMap();

      promise.resolve(map);
    } catch (Exception ex) {
      ex.printStackTrace();
      promise.reject(null, ex.getMessage());
    }
  }

  @ReactMethod
  public void getInstalledPackages(ReadableMap options, Promise promise) {
    try {
      WritableArray array = Arguments.createArray();

      boolean loadLabel = options != null && options.hasKey("loadLabel") ? options.getBoolean("loadLabel") : false;

      PackageManager pm = this.reactContext.getPackageManager();
      List<PackageInfo> packages = pm.getInstalledPackages(0);
      for (PackageInfo pi : packages) {
        PackageInfoMapping info = new PackageInfoMapping.Builder(pi, pm).withLabel(loadLabel).build();
        WritableMap map = info.asWritableMap();

        array.pushMap(map);
      }

      promise.resolve(array);
    } catch (Exception ex) {
      ex.printStackTrace();
      promise.reject(null, ex.getMessage());
    }
  }

  @ReactMethod
  public void uninstallApp(String packageName, Promise promise) {
    try {
      mUninstallPromise = promise;
      Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
      intent.setData(Uri.parse("package:" + packageName));
      intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
      getCurrentActivity().startActivityForResult(intent, UNINSTALL_REQUEST_CODE);
    } catch (Exception ex) {
      ex.printStackTrace();
      promise.reject(UNINSTALL_FAILED_KEY, ex.getMessage());
      mUninstallPromise = null;
    }
  }
}
