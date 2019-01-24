package com.blankj.subutil.util;

import android.content.Intent;
import android.net.Uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/26
 *     desc  : ClipboardUtils 单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ClipboardUtilsTest {

    static {
        TestUtils.init();
    }

    @Test
    public void testText() throws Exception {
        ClipboardUtils.copyText("test");
        assertEquals("test", ClipboardUtils.getText());
    }

    @Test
    public void testUri() throws Exception {
        ClipboardUtils.copyUri(Uri.parse("http://www.blankj.com"));
        System.out.println((ClipboardUtils.getUri()));
    }

    @Test
    public void testIntent() throws Exception {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_DIAL);
        ClipboardUtils.copyIntent(intent);
        System.out.println(ClipboardUtils.getText());
    }
}