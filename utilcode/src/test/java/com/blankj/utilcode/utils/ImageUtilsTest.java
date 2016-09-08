package com.blankj.utilcode.utils;

import android.graphics.Bitmap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.blankj.utilcode.utils.TestUtils.BASEPATH;
import static com.blankj.utilcode.utils.TestUtils.SEP;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/30
 *     desc  : ImageUtils单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ImageUtilsTest {

    String path = BASEPATH + "image" + SEP;

    @Test
    public void testBitmap2Bytes() throws Exception {
        Bitmap bitmap = ImageUtils.getBitmapByFile(path + "lena.png");
        System.out.println(ImageUtils.save(bitmap, path + "new.png", Bitmap.CompressFormat.PNG));
    }

    @Test
    public void testBytes2Bitmap() throws Exception {

    }

    @Test
    public void testDrawable2Bitmap() throws Exception {

    }

    @Test
    public void testBitmap2Drawable() throws Exception {

    }

    @Test
    public void testDrawable2Bytes() throws Exception {

    }

    @Test
    public void testBytes2Drawable() throws Exception {

    }

    @Test
    public void testGetBitmap() throws Exception {

    }

    @Test
    public void testScaleImage() throws Exception {

    }

    @Test
    public void testToRound() throws Exception {

    }

    @Test
    public void testToRoundCorner() throws Exception {

    }

    @Test
    public void testFastBlur() throws Exception {

    }

    @Test
    public void testRenderScriptBlur() throws Exception {

    }

    @Test
    public void testStackBlur() throws Exception {

    }

    @Test
    public void testAddFrame() throws Exception {

    }

    @Test
    public void testAddReflection() throws Exception {

    }

    @Test
    public void testSaveBitmap() throws Exception {

    }
}