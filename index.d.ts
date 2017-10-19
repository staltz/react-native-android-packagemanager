export interface PackageInfo {
  package: string;
  label: string;
  versionName: string;
  versionCode: number;
  firstInstallTime: number;
  lastUpdateTime: number;
}

declare class RNAndroidPackagemanager {
  static getPackageInfo(fullPath: string): Promise<PackageInfo>;
}

export default RNAndroidPackagemanager;
