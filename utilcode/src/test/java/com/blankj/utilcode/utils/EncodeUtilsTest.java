package com.blankj.utilcode.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/12
 *     desc  : EncodeUtils单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class EncodeUtilsTest {

    String urlEncodeString = "%E5%93%88%E5%93%88%E5%93%88";
    String html = "<html>" +
            "<head>" +
            "<title>我的第一个 HTML 页面</title>" +
            "</head>" +
            "<body>" +
            "<p>body 元素的内容会显示在浏览器中。</p>" +
            "<p>title 元素的内容会显示在浏览器的标题栏中。</p>" +
            "</body>" +
            "</html>";
    String encodeHtml = "&lt;html&gt;&lt;head&gt;&lt;title&gt;&#25105;&#30340;&#31532;&#19968;&#20010; HTML &#39029;&#38754;&lt;/title&gt;&lt;/head&gt;&lt;body&gt;&lt;p&gt;body &#20803;&#32032;&#30340;&#20869;&#23481;&#20250;&#26174;&#31034;&#22312;&#27983;&#35272;&#22120;&#20013;&#12290;&lt;/p&gt;&lt;p&gt;title &#20803;&#32032;&#30340;&#20869;&#23481;&#20250;&#26174;&#31034;&#22312;&#27983;&#35272;&#22120;&#30340;&#26631;&#39064;&#26639;&#20013;&#12290;&lt;/p&gt;&lt;/body&gt;&lt;/html&gt;";

    @Test
    public void testUrlEncode() throws Exception {
        assertThat(EncodeUtils.urlEncode("哈哈哈")).isEqualTo(urlEncodeString);
        assertThat(EncodeUtils.urlEncode("哈哈哈", "UTF-8")).isEqualTo(urlEncodeString);
    }

    @Test
    public void testUrlDecode() throws Exception {
        assertThat(EncodeUtils.urlDecode(urlEncodeString)).isEqualTo("哈哈哈");
        assertThat(EncodeUtils.urlDecode(urlEncodeString, "UTF-8")).isEqualTo("哈哈哈");
    }

    @Test
    public void testBase64Encode() throws Exception {
        assertThat(EncodeUtils.base64Encode("blankj")).isEqualTo("Ymxhbmtq");
    }

    @Test
    public void testBase64Decode() throws Exception {
        assertThat(EncodeUtils.base64Decode("Ymxhbmtq")).isEqualTo("blankj");
    }

    @Test
    public void testHtmlEncode() throws Exception {
        assertThat(EncodeUtils.htmlEncode(html)).isEqualTo(encodeHtml);
    }

    @Test
    public void testHtmlDecode() throws Exception {
        assertThat(EncodeUtils.htmlDecode(encodeHtml)).isEqualTo(html);
    }
}