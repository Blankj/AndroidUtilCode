package com.blankj.utilcode.utils;

public class CameraUtils{
    /**
     * 获取跳转至拍照程序界面的Intent
     */
    public static Intent gainToCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return intent;
    }

    /**
     * 跳转至拍照程序界面
     *
     * @param context    上下文
     * @param requestCode 请求返回Result区分代码
     */
    public static void toCameraActivity(Activity context, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 获取跳转至相册选择界面的Intent
     */
    public static Intent gainToImagePickerIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return intent;
    }

    /**
     * 跳转至相册选择界面 (必须选中的是 图片文件)
     *
     * @param context    上下文
     * @param requestCode int
     */
    public static void toImagePickerActivity(Activity context, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转至相册选择界面 (必须选中的是 视频文件)
     *
     * @param context    上下文
     * @param requestCode int
     */
    public static void toVideoPickerActivity(Activity context, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
        context.startActivityForResult(intent, requestCode);
    }


    /**
     * 获取[跳转至相册选择界面,并跳转至裁剪界面，默认可缩放裁剪区域]的Intent
     */
    public static Intent gainToImagePickerIntent(int outputX, int outputY, Uri fromFileURI,
                                                 Uri saveFileURI) {
        return gainToImagePickerIntent(1, 1, outputX, outputY, true, fromFileURI, saveFileURI);
    }

    /**
     * 获取[跳转至相册选择界面,并跳转至裁剪界面，默认可缩放裁剪区域]的Intent
     */
    public static Intent gainToImagePickerIntent(int aspectX, int aspectY, int outputX, int outputY, Uri fromFileURI,
                                                 Uri saveFileURI) {
        return gainToImagePickerIntent(aspectX, aspectY, outputX, outputY, true, fromFileURI, saveFileURI);
    }

    /**
     * 跳转至相册选择界面,并跳转至裁剪界面，默认可缩放裁剪区域
     *
     * @param context    上下文
     * @param requestCode 请求返回Result区分代码
     * @param outputX     输出尺寸宽度
     * @param outputY     输出尺寸高度
     * @param fromFileURI 文件来源路径URI
     * @param saveFileURI 输出文件路径URI
     */
    public static void toImagePickerActivity(Activity context, int requestCode, int outputX,
                                             int outputY, Uri fromFileURI, Uri saveFileURI) {
        toImagePickerActivity(context, requestCode, 1, 1, outputX, outputY, true, fromFileURI, saveFileURI);
    }

    /**
     * 跳转至相册选择界面,并跳转至裁剪界面，默认可缩放裁剪区域
     *
     * @param context    上下文
     * @param requestCode 请求返回Result区分代码
     * @param outputX     输出尺寸宽度
     * @param outputY     输出尺寸高度
     * @param fromFileURI 文件来源路径URI
     * @param saveFileURI 输出文件路径URI
     */
    public static void toImagePickerActivity(Activity context, int requestCode, int aspectX, int aspectY, int outputX,
                                             int outputY, Uri fromFileURI, Uri saveFileURI) {
        toImagePickerActivity(context, requestCode, aspectX, aspectY, outputX, outputY, true, fromFileURI, saveFileURI);
    }


    /**
     * 获取[跳转至相册选择界面,并跳转至裁剪界面，可以指定是否缩放裁剪区域]的Intent
     */
    public static Intent gainToImagePickerIntent(int aspectX, int aspectY, int outputX, int outputY, boolean canScale,
                                                 Uri fromFileURI, Uri saveFileURI) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(fromFileURI, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX <= 0 ? 1 : aspectX);
        intent.putExtra("aspectY", aspectY <= 0 ? 1 : aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", canScale);
        // 图片剪裁不足黑边解决
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveFileURI);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 去除人脸识别
        intent.putExtra("noFaceDetection", true);
        return intent;
    }
    /**
     * 跳转至相册选择界面,并跳转至裁剪界面，可以指定是否缩放裁剪区域
     *
     * @param context    上下文
     * @param requestCode 请求返回Result区分代码
     * @param aspectX     裁剪框尺寸比例X
     * @param aspectY     裁剪框尺寸比例Y
     * @param outputX     输出尺寸宽度
     * @param outputY     输出尺寸高度
     * @param canScale    是否可缩放
     * @param fromFileURI 文件来源路径URI
     * @param saveFileURI 输出文件路径URI
     */
    public static void toImagePickerActivity(Activity context, int requestCode, int aspectX, int aspectY, int outputX,
                                             int outputY, boolean canScale, Uri fromFileURI, Uri saveFileURI) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(fromFileURI, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX <= 0 ? 1 : aspectX);
        intent.putExtra("aspectY", aspectY <= 0 ? 1 : aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", canScale);
        // 图片剪裁不足黑边解决
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveFileURI);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 去除人脸识别
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, requestCode);
    }


    /**
     * 跳转至拍照程序界面，并且指定存储路径
     *
     * @param context    上下文
     * @param requestCode 请求返回Result区分代码
     * @param extraOutput 拍照存储路径URI
     */
    public static void toCameraActivity(Activity context, int requestCode, Uri extraOutput) {
        Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, extraOutput);
        context.startActivityForResult(mIntent, requestCode);
    }

    /**
     * 获取[跳转至相册选择界面,并跳转至裁剪界面，默认可缩放裁剪区域]的Intent
     */
    public static Intent gainToCameraIntent(Uri saveFileURI) {
        Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, saveFileURI);
        return mIntent;
    }

    /**
     * 获取[跳转至裁剪界面,默认可缩放]的Intent
     */
    public static Intent gainToCropImageIntent(int outputX, int outputY, Uri fromFileURI,
                                               Uri saveFileURI) {
        return gainToCropImageIntent(1, 1, outputX, outputY, true, fromFileURI, saveFileURI);
    }

    /**
     * 获取[跳转至裁剪界面,默认可缩放]的Intent
     */
    public static Intent gainToCropImageIntent(int aspectX, int aspectY, int outputX, int outputY, Uri fromFileURI,
                                               Uri saveFileURI) {
        return gainToCropImageIntent(aspectX, aspectY, outputX, outputY, true, fromFileURI, saveFileURI);
    }


    /**
     * 获取[跳转至裁剪界面]的Intent
     */
    public static Intent gainToCropImageIntent(int aspectX, int aspectY, int outputX, int outputY, boolean canScale,
                                               Uri fromFileURI, Uri saveFileURI) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fromFileURI, "image/*");
        intent.putExtra("crop", "true");
        // X方向上的比例
        intent.putExtra("aspectX", aspectX <= 0 ? 1 : aspectX);
        // Y方向上的比例
        intent.putExtra("aspectY", aspectY <= 0 ? 1 : aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", canScale);
        // 图片剪裁不足黑边解决
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        // 需要将读取的文件路径和裁剪写入的路径区分，否则会造成文件0byte
        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveFileURI);
        // true-->返回数据类型可以设置为Bitmap，但是不能传输太大，截大图用URI，小图用Bitmap或者全部使用URI
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 取消人脸识别功能
        intent.putExtra("noFaceDetection", true);
        return intent;
    }

    /**
     * 跳转至裁剪界面,默认可缩放
     *
     * @param context    启动裁剪界面的Activity
     * @param fromFileURI 需要裁剪的图片URI
     * @param saveFileURI 输出文件路径URI
     * @param aspectX     裁剪框尺寸比例X
     * @param aspectY     裁剪框尺寸比例Y
     * @param outputX     输出尺寸宽度
     * @param outputY     输出尺寸高度
     * @param requestCode 请求返回Result区分代码
     */
    public static void toCropImageActivity(Activity context, Uri fromFileURI, Uri saveFileURI, int aspectX, int aspectY,
                                           int outputX, int outputY, int requestCode) {
        toCropImageActivity(context, fromFileURI, saveFileURI, aspectX, aspectY, outputX, outputY, true, requestCode);
    }


    /**
     * 跳转至裁剪界面
     *
     * @param context    启动裁剪界面的Activity
     * @param fromFileURI 需要裁剪的图片URI
     * @param saveFileURI 输出文件路径URI
     * @param aspectX     裁剪框尺寸比例X
     * @param aspectY     裁剪框尺寸比例Y
     * @param outputX     输出尺寸宽度
     * @param outputY     输出尺寸高度
     * @param canScale    是否可缩放
     * @param requestCode 请求返回Result区分代码
     */
    public static void toCropImageActivity(Activity context, Uri fromFileURI, Uri saveFileURI, int aspectX, int aspectY,
                                           int outputX, int outputY, boolean canScale, int requestCode) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fromFileURI, "image/*");
        intent.putExtra("crop", "true");
        // X方向上的比例
        intent.putExtra("aspectX", aspectX <= 0 ? 1 : aspectX);
        // Y方向上的比例
        intent.putExtra("aspectY", aspectY <= 0 ? 1 : aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", canScale);
        // 图片剪裁不足黑边解决
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        // 需要将读取的文件路径和裁剪写入的路径区分，否则会造成文件0byte
        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveFileURI);
        // true-->返回数据类型可以设置为Bitmap，但是不能传输太大，截大图用URI，小图用Bitmap或者全部使用URI
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 取消人脸识别功能
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 获得选中相册的图片
     *
     * @param context 上下文
     * @param data     onActivityResult返回的Intent
     * @return
     */
    public static Bitmap getChoosedImage(Activity context, Intent data) {
        if (data == null) {
            return null;
        }
        Bitmap bm = null;
        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = context.getContentResolver();
        try {
            Uri originalUri = data.getData(); // 获得图片的uri
            bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // 显得到bitmap图片
        } catch (Exception e) {
            Log.e("ToolPhone", e.getMessage());
        }
        return bm;
    }
    /**
     * 获得选中相册的图片路径
     *
     * @param context 上下文
     * @param data     onActivityResult返回的Intent
     * @return
     */
    public static String getChoosedImagePath(Activity context, Intent data) {
        if (data == null) {
            return null;
        }
        String path = "";
        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = context.getContentResolver();
        // 此处的用于判断接收的Activity是不是你想要的那个
        Uri originalUri = data.getData(); // 获得图片的uri
        if (null == originalUri)
            return null;
        // 这里开始的第二部分，获取图片的路径：
        String[] proj = {MediaStore.Images.Media.DATA};
        // 好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor = resolver.query(originalUri, proj, null, null, null);
        if (null != cursor) {
            try {
                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                path = cursor.getString(column_index);
                LogUtil.d(path);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // 不用了关闭游标，4.0以上的版本会自动关闭
                    if (!cursor.isClosed()) {
                        cursor.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return TextUtils.isEmpty(path) ? originalUri.getPath() : path;
    }

    /**
     * 获取拍照之后的照片文件（JPG格式）
     *
     * @param data     onActivityResult回调返回的数据
     * @param filePath 文件存储路径（文件目录+文件名）
     * @param quality  文件存储质量（0-100）
     * @return
     */
    public static File getTakePictureFile(Intent data, String filePath, int quality) {
        // 数据合法性校验
        if (data == null) {
            return null;
        }
        Bundle extras = data.getExtras();
        if (extras == null) {
            return null;
        }

        // 保存图片
        Bitmap photo = extras.getParcelable("data");
        File file = new File(filePath);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            photo.compress(Bitmap.CompressFormat.JPEG, quality, stream);// (0-100)压缩文件
            stream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}