# react-native-android-packagemanager

Gives you some access to Android's [PackageManager](https://developer.android.com/reference/android/content/pm/PackageManager.html) APIs, for instance to read the metadata inside an APK file.

## Getting started

`$ npm install react-native-android-packagemanager --save`

### Easy installation

`$ react-native link react-native-android-packagemanager`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNAndroidPackagemanagerPackage;` to the imports at the top of the file
  - Add `new RNAndroidPackagemanagerPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
    ```
    include ':react-native-android-packagemanager'
    project(':react-native-android-packagemanager').projectDir = new File(rootProject.projectDir,   '../node_modules/react-native-android-packagemanager/android')
    ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
    ```
      compile project(':react-native-android-packagemanager')
    ```


## Usage
```javascript
import RNAndroidPM from 'react-native-android-packagemanager';

RNAndroidPM.getPackageInfo('/storage/emulated/0/myapp.apk')
  .then(info => {
    console.log(info);
    /*
      {
        package: "com.example.myapp",
        label: "My App",
        versionName: "1.2.3",
        versionCode: 3,
        firstInstallTime: 1185920,
        lastUpdateTime: 1283058,
        isSystemApp: false
      }
    */
  });

RNAndroidPM.getInstalledPackages({})
  .then(packages => {
    console.log(packages);
    /*
      [
        {
          package: "com.example.myapp",
          label: "My App",
          versionName: "1.2.3",
          versionCode: 3,
          firstInstallTime: 1185920,
          lastUpdateTime: 1283058,
          isSystemApp: false
        },
        {
          package: "com.example.anotherapp",
          label: "Another App",
          versionName: "1.0.0",
          versionCode: 1,
          firstInstallTime: 1185920,
          lastUpdateTime: 1283058,
          isSystemApp: false
        }
      ]
    */
  })
```
