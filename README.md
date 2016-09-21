## Android developers should collect the following utils
**[中文版README][readme.cn]**
***
Directory is shown below：  
> - **About App→[AppUtils.java][app.java]**
>  - *getInstallAppIntent*
>  - *getUninstallAppIntent*
>  - *getOpenAppItent*
>  - *getAppInfoIntent*
>  - *getShareInfoIntent*
>  - *isInstallApp*
>  - *getAppInfo*
>  - *getAllAppsInfo*
>  - *isAppBackground*

> - **About Const→[ConstUtils.java][const.java]**
>  - *Memory Const*
>  - *Time Const*
>  - *Regex Const*

> - **About Convert→[ConvertUtils.java][convert.java]→[Test][convert.test]**
>  - *bytes2HexString*、*hexString2Bytes*
>  - *chars2Bytes*、*bytes2Chars*
>  - *byte2Size*、*size2Byte*
>  - *byte2FitSize*
>  - *input2OutputStream*、*output2InputStream*
>  - *inputStream2Bytes*、*bytes2InputStream*
>  - *outputStream2Bytes*、*bytes2OutputStream*
>  - *inputStream2String*、*string2InputStream*
>  - *outputStream2String*、*string2OutputStream*
>  - *bitmap2Bytes*、*bytes2Bitmap*
>  - *drawable2Bitmap*、*bitmap2Drawable*
>  - *drawable2Bytes*、*bytes2Drawable*
>  - *dp2px*、*px2dp*
>  - *sp2px*、*px2sp*

> - **About Device→[DeviceUtils.java][device.java]**
>  - *getMacAddress*
>  - *getManufacturer*
>  - *getModel*

> - **About Encode→[EncodeUtils.java][encode.java]→[Test][encode.test]**
>  - *urlEncode*
>  - *urlDecode*
>  - *base64Encode*、*base64Encode2String*
>  - *base64Decode*
>  - *base64UrlSafeEncode*
>  - *htmlEncode*
>  - *htmlDecode*

> - **About Encrypt→[EncryptUtils.java][encrypt.java]→[Test][encrypt.test]**
>  - *encryptMD2ToString*、*encryptMD2*
>  - *encryptMD5ToString*、*encryptMD5*
>  - *encryptSHA1ToString*、*encryptSHA1*
>  - *encryptSHA224ToString*、*encryptSHA224*
>  - *encryptSHA256ToString*、*encryptSHA256*
>  - *encryptSHA384ToString*、*encryptSHA384*
>  - *encryptSHA512ToString*、*encryptSHA512*
>  - *encryptMD5File2String*、*encryptMD5File*
>  - *encryptDES2Base64*
>  - *encryptDES2HexString*
>  - *encryptDES*
>  - *decryptBase64DES*
>  - *decryptHexStringDES*
>  - *decryptDES*
>  - *encrypt3DES2Base64*
>  - *encrypt3DES2HexString*
>  - *encrypt3DES*
>  - *decryptBase64_3DES*
>  - *decryptHexString3DES*
>  - *decrypt3DES*
>  - *encryptAES2Base64*
>  - *encryptAES2HexString*
>  - *encryptAES*
>  - *decryptBase64AES*
>  - *decryptHexStringAES*
>  - *decryptAES*

> - **About File→[FileUtils.java][file.java]→[Test][file.test]**
>  - *getFileByPath*
>  - *isFileExists*
>  - *isDir*
>  - *isFile*
>  - *createOrExistsDir*
>  - *createOrExistsFile*
>  - *createFileByDeleteOldFile*
>  - *copyDir*
>  - *copyFile*
>  - *moveDir*
>  - *moveFile*
>  - *deleteDir*
>  - *deleteFile*
>  - *listFilesInDir*
>  - *listFilesInDir*
>  - *listFilesInDirWithFilter*
>  - *listFilesInDirWithFilter*
>  - *listFilesInDirWithFilter*
>  - *listFilesInDirWithFilter*
>  - *searchFileInDir*
>  - *writeFileFromIS*
>  - *writeFileFromString*
>  - *getFileCharsetSimple*
>  - *getFileLines*
>  - *readFile2List*
>  - *readFile2SB*
>  - *getFileSize*
>  - *getFileMD5*
>  - *closeIO*
>  - *getDirName*
>  - *getFileName*
>  - *getFileNameNoExtension*
>  - *getFileExtension*

> - **About Image→[ImageUtils.java][image.java]**
>  - *bitmap2Bytes*、*bytes2Bitmap*
>  - *drawable2Bitmap*、*bitmap2Drawable*
>  - *drawable2Bytes*、*bytes2Drawable*
>  - *getBitmap*
>  - *scale*
>  - *clip*
>  - *skew*
>  - *rotate*
>  - *getRotateDegree*
>  - *toRound*
>  - *toRoundCorner*
>  - *fastBlur*
>  - *renderScriptBlur*
>  - *stackBlur*
>  - *addFrame*
>  - *addReflection*
>  - *addTextWatermark*
>  - *addImageWatermark*
>  - *toAlpha*
>  - *toGray*
>  - *save*
>  - *isImage*
>  - *getImageType*
>  - *compressByScale*
>  - *compressByQuality*
>  - *compressBySampleSize*

> - **About Keyboard→[KeyboardUtils.java][keyboard.java]**
>  - *hideSoftInput*
>  - *clickBlankArea2HideSoftInput0*
>  - *showSoftInput*
>  - *toggleSoftInput*

> - **About Network→[NetworkUtils.java][network.java]**
>  - *openWirelessSettings*
>  - *isAvailable*
>  - *isConnected*
>  - *is4G*
>  - *isWifiConnected*
>  - *getNetworkOperatorName*
>  - *getPhoneType*
>  - *getNetWorkType*、*getNetWorkTypeName*

> - **About Phone→[PhoneUtils.java][phone.java]**
>  - *isPhone*
>  - *getPhoneIMEI*
>  - *getPhoneStatus*
>  - *dial*
>  - *call*
>  - *sendSms*
>  - *getAllContactInfo*
>  - *getContantNum*
>  - *getAllSMS*

> - **About Regex→[RegexUtils.java][regex.java]→[Test][regex.test]**
>  - *isMobileSimple*
>  - *isMobileExact*
>  - *isTel*
>  - *isIDCard15*
>  - *isIDCard18*
>  - *isEmail*
>  - *isURL*
>  - *isChz*
>  - *isUsername*
>  - *isDate*
>  - *isIP*
>  - *isMatch*

> - **About Screen→[ScreenUtils.java][screen.java]**
>  - *getDeviceWidth*、*getDeviceHeight*
>  - *setTransparentStatusBar*
>  - *hideStatusBar*
>  - *getStatusBarHeight*
>  - *isStatusBarExists*
>  - *getActionBarHeight*
>  - *showNotificationBar*
>  - *hideNotificationBar*
>  - *setLandscape*
>  - *snapShotWithStatusBar*、*snapShotWithoutStatusBar*
>  - *isScreenLock*

> - **About SDCard→[SDCardUtils.java][sdcard.java]**
>  - *isSDCardEnable*
>  - *getSDCardPath*
>  - *getDataPath*
>  - *getFreeSpace*

> - **About Shell→[ShellUtils.java][shell.java]**
>  - *isRoot*
>  - *execCmd*

> - **About Size→[SizeUtils.java][size.java]**
>  - *dp2px*、*px2dp*
>  - *sp2px*、*px2sp*
>  - *applyDimension*
>  - *forceGetViewSize*
>  - *measureView*

> - **About SP→[SPUtils.java][sp.java]→[Test][sp.test]**
>  - *SPUtils*
>  - *putString*
>  - *getString*
>  - *putInt*
>  - *getInt*
>  - *putLong*
>  - *getLong*
>  - *putFloat*
>  - *getFloat*
>  - *putBoolean*
>  - *getBoolean*
>  - *getAll*
>  - *remove*
>  - *contains*
>  - *clear*

> - **About String→[StringUtils.java][string.java]→[Test][string.test]**
>  - *isEmpty*
>  - *isSpace*
>  - *null2Length0*
>  - *length*
>  - *upperFirstLetter*
>  - *lowerFirstLetter*
>  - *reverse*
>  - *toDBC*
>  - *toSBC*

> - **About ThreadPool→[ThreadPoolUtils.java][thread_pool.java]**
>  - *ThreadPoolUtils*
>  - *execute*
>  - *execute*
>  - *shutDown*
>  - *shutDownNow*
>  - *isShutDown*
>  - *isTerminated*
>  - *awaitTermination*
>  - *submit*
>  - *submit*
>  - *invokeAll*、*invokeAny*
>  - *schedule*
>  - *schedule*
>  - *scheduleWithFixedRate*、*scheduleWithFixedDelay*

> - **About Time→[TimeUtils.java][time.java]→[Test][time.test]**
>  - *milliseconds2String*
>  - *string2Milliseconds*
>  - *string2Date*
>  - *date2String*
>  - *date2Milliseconds*
>  - *milliseconds2Date*
>  - *milliseconds2Unit*
>  - *getIntervalTime*
>  - *getCurTimeMills*、*getCurTimeString*、*getCurTimeDate*
>  - *getIntervalByNow*
>  - *isLeapYear*

> - **About Unclassified→[UnclassifiedUtils.java][unclassified.java]**
>  - *isRunningService*

> - **About Zip→[ZipUtils.java][zip.java]→[Test][zip.test]**
>  - *zipFiles*
>  - *zipFile*
>  - *unzipFiles*
>  - *unzipFile*
>  - *unzipFileByKeyword*
>  - *getFilesPath*
>  - *getComments*
>  - *getEntries*

> - **Update Log→[update_log.md][update_log.md]**

***
  
**I'm so sorry for that the code is annotated with Chinese.**  

### Download
***
Gradle:
``` groovy
compile 'com.blankj:utilcode:1.2.1'
```

### Proguard
***
```
-keep class com.blankj.utilcode.** { *; }
-keep classmembers class com.blankj.utilcode.** { *; }
-dontwarn com.blankj.utilcode.**
```

### License
***
```
Copyright 2016 Blankj

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[readme.cn]: https://github.com/Blankj/AndroidUtilCode/blob/master/README-CN.md

[app.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/AppUtils.java

[const.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ConstUtils.java

[convert.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ConvertUtils.java
[convert.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/ConvertUtilsTest.java

[device.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/DeviceUtils.java

[encode.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/EncodeUtils.java
[encode.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/EncodeUtilsTest.java

[encrypt.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/EncryptUtils.java
[encrypt.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/EncryptUtilsTest.java

[file.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/FileUtils.java
[file.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/FileUtilsTest.java

[image.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ImageUtils.java

[keyboard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/KeyboardUtils.java

[network.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/NetworkUtils.java

[phone.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/PhoneUtils.java

[regex.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/RegexUtils.java
[regex.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/RegexUtilsTest.java

[screen.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ScreenUtils.java

[sdcard.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SDCardUtils.java

[shell.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ShellUtils.java

[size.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SizeUtils.java

[sp.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/SPUtils.java
[sp.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/SPUtilsTest.java

[string.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/StringUtils.java
[string.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/StringUtilsTest.java

[thread_pool.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ThreadPoolUtils.java

[time.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/TimeUtils.java
[time.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/TimeUtilsTest.java

[unclassified.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/UnclassifiedUtils.java

[zip.java]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/utils/ZipUtils.java
[zip.test]: https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/test/java/com/blankj/utilcode/utils/ZipUtilsTest.java

[update_log.md]: https://github.com/Blankj/AndroidUtilCode/blob/master/update_log.md