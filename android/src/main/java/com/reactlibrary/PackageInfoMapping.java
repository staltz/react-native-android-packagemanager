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
    map.putDouble("versionCode", this.packageInfo.versionCode);
    map.putDouble("firstInstallTime", this.packageInfo.firstInstallTime);
    map.putDouble("lastUpdateTime", this.packageInfo.lastUpdateTime);

    return map;
  }

  private String loadPackageLabel() {
    String label = this.applicationInfo.packageName;
    try
    {
        label = this.applicationInfo.loadLabel(this.packageManager).toString();
    }
    catch (Exception exc) { }
    return label;
  }
}
