package com.blankj.utilcode.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/12
 *     desc  : EncodeUtils单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class EncodeUtilsTest {

    String urlEncodeString = "%E5%93%88%E5%93%88%E5%93%88";
    String html            = "<html>" +
            "<head>" +
            "<title>我的第一个 HTML 页面</title>" +
            "</head>" +
            "<body>" +
            "<p>body 元素的内容会显示在浏览器中。</p>" +
            "<p>title 元素的内容会显示在浏览器的标题栏中。</p>" +
            "</body>" +
            "</html>";
    String encodeHtml      = "&lt;html&gt;&lt;head&gt;&lt;title&gt;我的第一个 HTML 页面&lt;/title&gt;&lt;/head&gt;&lt;body&gt;&lt;p&gt;body 元素的内容会显示在浏览器中。&lt;/p&gt;&lt;p&gt;title 元素的内容会显示在浏览器的标题栏中。&lt;/p&gt;&lt;/body&gt;&lt;/html&gt;";

    @Test
    public void urlEncode_urlDecode() throws Exception {
        Assert.assertEquals(urlEncodeString, EncodeUtils.urlEncode("哈哈哈"));
        Assert.assertEquals(urlEncodeString, EncodeUtils.urlEncode("哈哈哈", "UTF-8"));

        Assert.assertEquals("哈哈哈", EncodeUtils.urlDecode(urlEncodeString));
        Assert.assertEquals("哈哈哈", EncodeUtils.urlDecode(urlEncodeString, "UTF-8"));
    }

    @Test
    public void base64Decode_base64Encode() throws Exception {
        Assert.assertTrue(
                Arrays.equals(
                        "blankj".getBytes(),
                        EncodeUtils.base64Decode(EncodeUtils.base64Encode("blankj"))
                )
        );
        Assert.assertTrue(
                Arrays.equals(
                        "blankj".getBytes(),
                        EncodeUtils.base64Decode(EncodeUtils.base64Encode2String("blankj".getBytes()))
                )
        );
        Assert.assertEquals(
                "Ymxhbmtq",
                EncodeUtils.base64Encode2String("blankj".getBytes())
        );
        Assert.assertTrue(
                Arrays.equals(
                        "Ymxhbmtq".getBytes(),
                        EncodeUtils.base64Encode("blankj".getBytes())
                )
        );
    }

    @Test
    public void htmlEncode_htmlDecode() throws Exception {
        Assert.assertEquals(encodeHtml, EncodeUtils.htmlEncode(html));

        Assert.assertEquals(html, EncodeUtils.htmlDecode(encodeHtml).toString());
    }
}