package com.blankj.utilcode.utils;

import android.content.ContentResolver;
import android.net.Uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import java.io.File;

import static com.google.common.truth.Truth.assertThat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/26
 *     desc  : ClipboardUtils单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ClipboardUtilsTest {
    @Test
    public void testText() throws Exception {
        ClipboardUtils.copyText(TestUtils.getContext(), "test");
        assertThat(ClipboardUtils.getText(TestUtils.getContext())).isEqualTo("test");
    }

    @Test
    public void testUri() throws Exception {
        ClipboardUtils.copyUri(TestUtils.getContext(), Uri.parse("http://www.blankj.com"));
        System.out.println((ClipboardUtils.getUri(TestUtils.getContext())));
    }

    @Test
    public void testIntent() throws Exception {
//        ClipboardUtils.copyIntent(TestUtils.getContext(), IntentUtils.getShareTextIntent("test"));
//        System.out.println(ClipboardUtils.getText(TestUtils.getContext()));
        System.out.println(NetworkUtils.getIpAddress("blankjv.com"));
    }
}