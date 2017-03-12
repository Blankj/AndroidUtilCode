package com.blankj.utilcode.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by yons on 17/2/10.
 */

public class UriUtils {

    private UriUtils(){
        throw new UnsupportedOperationException("u can't get me...");
    }

    /**
     * 获得图片文件的Uri
     *
     * @param file 图片文件
     * @return 图片的Uri
     */
    public static Uri getImageUri(File file) {
        String absPath = file.getAbsolutePath();

        Cursor cursor = Utils.getContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , new String[]{MediaStore.Images.Media._ID}
                , MediaStore.Images.Media.DATA + "=? "
                , new String[]{absPath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(id));

        } else if (!absPath.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, absPath);
            return Utils.getContext().getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    /**
     * 将bitmap转换成Uri
     *
     * @param bitmap 需要转换的bitmap
     * @return 转换后的Uri
     */
    public static Uri bitmap2Uri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(Utils.getContext().getContentResolver(), bitmap, "title", null);
        return Uri.parse(path);
    }
}
