package com.reactlibrary;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

public class PackageInfoMapping {

  private String packageName;
  private String label;
  private String versionName;
  private int versionCode;
  private long firstInstallTime;
  private long lastUpdateTime;

  private PackageInfoMapping(PackageInfo packageInfo, String label) {
    this.label = label;
    this.packageName = packageInfo.packageName;
    this.versionName = packageInfo.versionName;
    this.versionCode = packageInfo.versionCode;
    this.firstInstallTime = packageInfo.firstInstallTime;
    this.lastUpdateTime = packageInfo.lastUpdateTime;
  }

  public WritableMap asWritableMap() {
    WritableMap map = Arguments.createMap();

    map.putString("label", this.label);
    map.putString("package", this.packageName);
    map.putString("versionName", this.versionName);
    map.putInt("versionCode", this.versionCode);
    map.putDouble("firstInstallTime", this.firstInstallTime);
    map.putDouble("lastUpdateTime", this.lastUpdateTime);

    return map;
  }

  public static class Builder {

    private boolean loadLabel;
    private PackageInfo packageInfo;
    private PackageManager packageManager;
    private ApplicationInfo applicationInfo;

    public Builder(PackageInfo packageInfo, PackageManager packageManager) {
      this.packageInfo = packageInfo;
      this.packageManager = packageManager;
      this.applicationInfo = packageInfo.applicationInfo;
    }

    public Builder withLabel(boolean loadLabel) {
      this.loadLabel = loadLabel;
      return this;
    }

    public PackageInfoMapping build() {
      String label = this.loadLabel ? this.loadPackageLabel() : null;
      return new PackageInfoMapping(this.packageInfo, label);
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
}
