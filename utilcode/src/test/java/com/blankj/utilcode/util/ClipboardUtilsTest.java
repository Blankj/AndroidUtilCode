package com.blankj.utilcode.util;

import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

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

    @Before
    public void setUp() throws Exception {
        if (Utils.getContext() == null) TestUtils.init();
    }

    @Test
    public void testText() throws Exception {
        ClipboardUtils.copyText("test");
        assertThat(ClipboardUtils.getText()).isEqualTo("test");
    }

    @Test
    public void testUri() throws Exception {
        ClipboardUtils.copyUri(Uri.parse("http://www.blankj.com"));
        System.out.println((ClipboardUtils.getUri()));
    }

    @Test
    public void testIntent() throws Exception {
        ClipboardUtils.copyIntent(IntentUtils.getShareTextIntent("test"));
        System.out.println(ClipboardUtils.getText());
    }
}