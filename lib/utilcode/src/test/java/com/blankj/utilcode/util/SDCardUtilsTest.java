package com.blankj.utilcode.util;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import org.junit.Test;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowEnvironment;
import org.robolectric.shadows.ShadowStatFs;
import org.robolectric.shadows.ShadowStorageManager;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * {@link SDCardUtils}单元测试
 * Created by liyujiang on 2019/12/13
 */
public class SDCardUtilsTest extends BaseTest {

    @Test
    public void testSDCardEnable() {
        ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED);
        assertTrue(SDCardUtils.isSDCardEnableByEnvironment());
        assertNotEquals(SDCardUtils.getSDCardPathByEnvironment(), "");
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        System.out.println(file.getAbsolutePath());
        assertTrue(file.exists());
    }

    @Test
    public void testSDCardInfo() {
        ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED);
        StorageManager sm = (StorageManager) RuntimeEnvironment.application.getSystemService(Context.STORAGE_SERVICE);
        ShadowStorageManager shadowStorageManager = Shadows.shadowOf(sm);
        System.out.println(Arrays.deepToString(shadowStorageManager.getVolumeList()));
        System.out.println(Arrays.deepToString(SDCardUtils.getMountedSDCardPath().toArray()));
    }

    @Test
    public void testAvailableSpaceSize() {
        ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED);
        ShadowStatFs.reset();
        String extPath = Environment.getExternalStorageDirectory().toString();
        System.out.println(extPath);
        ShadowStatFs.registerStats(extPath, 5, 1, 4);
        assertEquals(SDCardUtils.getAvailableSpaceSize(), ShadowStatFs.BLOCK_SIZE * 4);
        ShadowEnvironment.setExternalStorageState(Environment.MEDIA_REMOVED);
        assertFalse(SDCardUtils.isSDCardEnableByEnvironment());
        String innPath = Utils.getApp().getFilesDir().getAbsolutePath();
        System.out.println(innPath);
        ShadowEnvironment.addExternalDir(innPath);
        ShadowStatFs.registerStats(innPath, 10, 8, 2);
        assertEquals(SDCardUtils.getAvailableSpaceSize(), ShadowStatFs.BLOCK_SIZE * 2);
        assertEquals(SDCardUtils.getAvailableSpaceSize(innPath), ShadowStatFs.BLOCK_SIZE * 2);
    }

}
