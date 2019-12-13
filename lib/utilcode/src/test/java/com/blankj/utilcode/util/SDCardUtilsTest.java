package com.blankj.utilcode.util;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import org.junit.After;
import org.junit.Test;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowEnvironment;
import org.robolectric.shadows.ShadowStatFs;
import org.robolectric.shadows.ShadowStorageManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * {@link SDCardUtils}单元测试
 * Created by liyujiang on 2019/12/13
 */
public class SDCardUtilsTest extends BaseTest {

    @After
    public void tearDown() throws Exception {
        ShadowEnvironment.setExternalStorageState(Environment.MEDIA_REMOVED);
        ShadowStatFs.reset();
    }

    @Test
    public void testSDCard() {
        ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED);
        assertTrue(SDCardUtils.isSDCardEnableByEnvironment());
        assertNotEquals(SDCardUtils.getSDCardPathByEnvironment(), "");
        assertTrue(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).exists());

        StorageManager sm = (StorageManager) Utils.getApp().getSystemService(Context.STORAGE_SERVICE);
        ShadowStorageManager shadowStorageManager = Shadows.shadowOf(sm);
        shadowStorageManager.resetStorageVolumeList();
        assertEquals(SDCardUtils.getSDCardInfo().size(), 0);

        String extPath = Environment.getExternalStorageDirectory().toString();
        ShadowStatFs.registerStats(extPath, 5, 1, 4);
        assertEquals(SDCardUtils.getAvailableSpaceSize(), ShadowStatFs.BLOCK_SIZE * 4);
        ShadowEnvironment.setExternalStorageState(Environment.MEDIA_REMOVED);
        assertFalse(SDCardUtils.isSDCardEnableByEnvironment());

        String innPath = Utils.getApp().getFilesDir().getAbsolutePath();
        ShadowEnvironment.addExternalDir(innPath);
        ShadowStatFs.registerStats(innPath, 10, 8, 2);
        assertEquals(SDCardUtils.getAvailableSpaceSize(), ShadowStatFs.BLOCK_SIZE * 2);
    }

}
