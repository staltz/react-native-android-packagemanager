package com.reactlibrary;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

public class PackageInfoMapping {

  private PackageInfo packageInfo;
  private PackageManager packageManager;
  private ApplicationInfo applicationInfo;

  public PackageInfoMapping(PackageInfo packageInfo, PackageManager packageManager) {
    this.packageInfo = packageInfo;
    this.packageManager = packageManager;
    this.applicationInfo = packageInfo.applicationInfo;
  }

  public WritableMap asWritableMap() {
    WritableMap map = Arguments.createMap();

    String label = loadPackageLabel();
    map.putString("label", label);

    map.putString("package", this.packageInfo.packageName);
    map.putString("versionName", this.packageInfo.versionName);
    map.putInt("versionCode", this.packageInfo.versionCode);
    map.putDouble("firstInstallTime", this.packageInfo.firstInstallTime);
    map.putDouble("lastUpdateTime", this.packageInfo.lastUpdateTime);
    map.putBoolean("isSystemApp", (this.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);

    return map;
  }

  private String loadPackageLabel() {
    String label;
    try {
      label = this.applicationInfo.loadLabel(this.packageManager).toString();
    }
    catch (Exception exc) {
      label = this.applicationInfo.packageName;
    }
    return label;
  }
}
