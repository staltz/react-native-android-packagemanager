package com.reactlibrary;

import android.content.pm.PackageInfo;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

public class PackageInfoMapping {

  private PackageInfo packageInfo;
  private String label;

  public PackageInfoMapping(PackageInfo packageInfo, String label) {
      this.packageInfo = packageInfo;
      this.label = label;
  }

  public WritableMap asWritableMap() {
    WritableMap map = Arguments.createMap();

    map.putString("label", this.label);
    map.putString("package", this.packageInfo.packageName);
    map.putString("versionName", this.packageInfo.versionName);
    map.putDouble("versionCode", this.packageInfo.versionCode);
    map.putDouble("firstInstallTime", this.packageInfo.firstInstallTime);
    map.putDouble("lastUpdateTime", this.packageInfo.lastUpdateTime);

    return map;
  }
}
