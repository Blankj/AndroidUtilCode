package com.blankj.utilcode.util;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/23
 *     desc  : 意图相关工具类
 * </pre>
 */
public final class IntentUtils {

    private IntentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取安装 App（支持 8.0）的意图
     * <p>8.0 需添加权限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath  文件路径
     * @param authority 7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @return 安装 App（支持 8.0）的意图
     */
    public static Intent getInstallAppIntent(final String filePath, final String authority) {
        return getInstallAppIntent(FileUtils.getFileByPath(filePath), authority);
    }

    /**
     * 获取安装 App(支持 8.0)的意图
     * <p>8.0 需添加权限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file      文件
     * @param authority 7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @return 安装 App(支持 8.0)的意图
     */
    public static Intent getInstallAppIntent(final File file, final String authority) {
        return getInstallAppIntent(file, authority, false);
    }

    /**
     * 获取安装 App(支持 8.0)的意图
     * <p>8.0 需添加权限
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file      文件
     * @param authority 7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
     *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @param isNewTask 是否开启新的任务栈
     * @return 安装 App(支持 8.0)的意图
     */
    public static Intent getInstallAppIntent(final File file,
                                             final String authority,
                                             final boolean isNewTask) {
        if (file == null) return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            data = FileProvider.getUriForFile(Utils.getApp(), authority, file);
        }
        intent.setDataAndType(data, type);
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取卸载 App 的意图
     *
     * @param packageName 包名
     * @return 卸载 App 的意图
     */
    public static Intent getUninstallAppIntent(final String packageName) {
        return getUninstallAppIntent(packageName, false);
    }

    /**
     * 获取卸载 App 的意图
     *
     * @param packageName 包名
     * @param isNewTask   是否开启新的任务栈
     * @return 卸载 App 的意图
     */
    public static Intent getUninstallAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取打开 App 的意图
     *
     * @param packageName 包名
     * @return 打开 App 的意图
     */
    public static Intent getLaunchAppIntent(final String packageName) {
        return getLaunchAppIntent(packageName, false);
    }

    /**
     * 获取打开 App 的意图
     *
     * @param packageName 包名
     * @param isNewTask   是否开启新的任务栈
     * @return 打开 App 的意图
     */
    public static Intent getLaunchAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = Utils.getApp().getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) return null;
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取 App 具体设置的意图
     *
     * @param packageName 包名
     * @return App 具体设置的意图
     */
    public static Intent getAppDetailsSettingsIntent(final String packageName) {
        return getAppDetailsSettingsIntent(packageName, false);
    }

    /**
     * 获取 App 具体设置的意图
     *
     * @param packageName 包名
     * @param isNewTask   是否开启新的任务栈
     * @return App 具体设置的意图
     */
    public static Intent getAppDetailsSettingsIntent(final String packageName,
                                                     final boolean isNewTask) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取分享文本的意图
     *
     * @param content 分享文本
     * @return 分享文本的意图
     */
    public static Intent getShareTextIntent(final String content) {
        return getShareTextIntent(content, false);
    }

    /**
     * 获取分享文本的意图
     *
     * @param content   分享文本
     * @param isNewTask 是否开启新的任务栈
     * @return 分享文本的意图
     */

    public static Intent getShareTextIntent(final String content, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取分享图片的意图
     *
     * @param content   文本
     * @param imagePath 图片文件路径
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(final String content, final String imagePath) {
        return getShareImageIntent(content, imagePath, false);
    }

    /**
     * 获取分享图片的意图
     *
     * @param content   文本
     * @param imagePath 图片文件路径
     * @param isNewTask 是否开启新的任务栈
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(final String content,
                                             final String imagePath,
                                             final boolean isNewTask) {
        if (imagePath == null || imagePath.length() == 0) return null;
        return getShareImageIntent(content, new File(imagePath), isNewTask);
    }

    /**
     * 获取分享图片的意图
     *
     * @param content 文本
     * @param image   图片文件
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(final String content, final File image) {
        return getShareImageIntent(content, image, false);
    }

    /**
     * 获取分享图片的意图
     *
     * @param content   文本
     * @param image     图片文件
     * @param isNewTask 是否开启新的任务栈
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(final String content,
                                             final File image,
                                             final boolean isNewTask) {
        if (image != null && image.isFile()) return null;
        return getShareImageIntent(content, Uri.fromFile(image), isNewTask);
    }

    /**
     * 获取分享图片的意图
     *
     * @param content 分享文本
     * @param uri     图片 uri
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(final String content, final Uri uri) {
        return getShareImageIntent(content, uri, false);
    }

    /**
     * 获取分享图片的意图
     *
     * @param content   分享文本
     * @param uri       图片 uri
     * @param isNewTask 是否开启新的任务栈
     * @return 分享图片的意图
     */
    public static Intent getShareImageIntent(final String content,
                                             final Uri uri,
                                             final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @return 其他应用组件的意图
     */
    public static Intent getComponentIntent(final String packageName, final String className) {
        return getComponentIntent(packageName, className, null, false);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @param isNewTask   是否开启新的任务栈
     * @return 其他应用组件的意图
     */
    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final boolean isNewTask) {
        return getComponentIntent(packageName, className, null, isNewTask);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     * @return 其他应用组件的意图
     */
    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final Bundle bundle) {
        return getComponentIntent(packageName, className, bundle, false);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     * @param isNewTask   是否开启新的任务栈
     * @return 其他应用组件的意图
     */
    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final Bundle bundle,
                                            final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (bundle != null) intent.putExtras(bundle);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取关机的意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.SHUTDOWN" />}</p>
     *
     * @return 关机的意图
     */
    public static Intent getShutdownIntent() {
        return getShutdownIntent(false);
    }

    /**
     * 获取关机的意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.SHUTDOWN" />}</p>
     *
     * @param isNewTask 是否开启新的任务栈
     * @return 关机的意图
     */
    public static Intent getShutdownIntent(final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SHUTDOWN);
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取跳至拨号界面意图
     *
     * @param phoneNumber 电话号码
     * @return 跳至拨号界面意图
     */
    public static Intent getDialIntent(final String phoneNumber) {
        return getDialIntent(phoneNumber, false);
    }

    /**
     * 获取跳至拨号界面意图
     *
     * @param phoneNumber 电话号码
     * @param isNewTask   是否开启新的任务栈
     * @return 跳至拨号界面意图
     */
    public static Intent getDialIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取拨打电话意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber 电话号码
     * @return 拨打电话意图
     */
    public static Intent getCallIntent(final String phoneNumber) {
        return getCallIntent(phoneNumber, false);
    }

    /**
     * 获取拨打电话意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber 电话号码
     * @param isNewTask   是否开启新的任务栈
     * @return 拨打电话意图
     */
    public static Intent getCallIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取发送短信界面的意图
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @return 发送短信界面的意图
     */
    public static Intent getSendSmsIntent(final String phoneNumber, final String content) {
        return getSendSmsIntent(phoneNumber, content, false);
    }

    /**
     * 获取跳至发送短信界面的意图
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @param isNewTask   是否开启新的任务栈
     * @return 发送短信界面的意图
     */
    public static Intent getSendSmsIntent(final String phoneNumber,
                                          final String content,
                                          final boolean isNewTask) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取拍照的意图
     *
     * @param outUri 输出的 uri
     * @return 拍照的意图
     */
    public static Intent getCaptureIntent(final Uri outUri) {
        return getCaptureIntent(outUri, false);
    }

    /**
     * 获取拍照的意图
     *
     * @param outUri    输出的 uri
     * @param isNewTask 是否开启新的任务栈
     * @return 拍照的意图
     */
    public static Intent getCaptureIntent(final Uri outUri, final boolean isNewTask) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return getIntent(intent, isNewTask);
    }

    private static Intent getIntent(final Intent intent, final boolean isNewTask) {
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

//    /**
//     * 获取选择照片的 Intent
//     *
//     * @return
//     */
//    public static Intent getPickIntentWithGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        return intent.setType("image*//*");
//    }
//
//    /**
//     * 获取从文件中选择照片的 Intent
//     *
//     * @return
//     */
//    public static Intent getPickIntentWithDocuments() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        return intent.setType("image*//*");
//    }
//
//
//    public static Intent buildImageGetIntent(final Uri saveTo, final int outputX, final int outputY, final boolean returnData) {
//        return buildImageGetIntent(saveTo, 1, 1, outputX, outputY, returnData);
//    }
//
//    public static Intent buildImageGetIntent(Uri saveTo, int aspectX, int aspectY,
//                                             int outputX, int outputY, boolean returnData) {
//        Intent intent = new Intent();
//        if (Build.VERSION.SDK_INT < 19) {
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//        } else {
//            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//        }
//        intent.setType("image*//*");
//        intent.putExtra("output", saveTo);
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", returnData);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//        return intent;
//    }
//
//    public static Intent buildImageCropIntent(final Uri uriFrom, final Uri uriTo, final int outputX, final int outputY, final boolean returnData) {
//        return buildImageCropIntent(uriFrom, uriTo, 1, 1, outputX, outputY, returnData);
//    }
//
//    public static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY,
//                                              int outputX, int outputY, boolean returnData) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uriFrom, "image*//*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("output", uriTo);
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", returnData);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//        return intent;
//    }
//
//    public static Intent buildImageCaptureIntent(final Uri uri) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        return intent;
//    }
}
