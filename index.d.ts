export interface PackageInfo {
  package: string;
  label: string;
  versionName: string;
  versionCode: number;
  firstInstallTime: number;
  lastUpdateTime: number;
  isSystemApp: boolean;
}

declare class RNAndroidPackagemanager {
  static getPackageInfo(fullPath: string): Promise<PackageInfo>;
  static getInstalledPackages(options: {loadLabel: boolean}): Promise<PackageInfo[]>;
}

export default RNAndroidPackagemanager;
