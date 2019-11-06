package com.blankj.utilcode.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/12
 *     desc  : test EncodeUtils
 * </pre>
 */
public class EncodeUtilsTest extends BaseTest {

    @Test
    public void urlEncode_urlDecode() {
        String urlEncodeString = "%E5%93%88%E5%93%88%E5%93%88";
        assertEquals(urlEncodeString, EncodeUtils.urlEncode("哈哈哈"));
        assertEquals(urlEncodeString, EncodeUtils.urlEncode("哈哈哈", "UTF-8"));

        assertEquals("哈哈哈", EncodeUtils.urlDecode(urlEncodeString));
        assertEquals("哈哈哈", EncodeUtils.urlDecode(urlEncodeString, "UTF-8"));
    }

    @Test
    public void base64Decode_base64Encode() {
        assertArrayEquals("blankj".getBytes(), EncodeUtils.base64Decode(EncodeUtils.base64Encode("blankj")));
        assertArrayEquals("blankj".getBytes(), EncodeUtils.base64Decode(EncodeUtils.base64Encode2String("blankj".getBytes())));
        assertEquals(
                "Ymxhbmtq",
                EncodeUtils.base64Encode2String("blankj".getBytes())
        );
        assertArrayEquals("Ymxhbmtq".getBytes(), EncodeUtils.base64Encode("blankj".getBytes()));
    }

    @Test
    public void htmlEncode_htmlDecode() {
        String html = "<html>" +
                "<head>" +
                "<title>我的第一个 HTML 页面</title>" +
                "</head>" +
                "<body>" +
                "<p>body 元素的内容会显示在浏览器中。</p>" +
                "<p>title 元素的内容会显示在浏览器的标题栏中。</p>" +
                "</body>" +
                "</html>";
        String encodeHtml = "&lt;html&gt;&lt;head&gt;&lt;title&gt;我的第一个 HTML 页面&lt;/title&gt;&lt;/head&gt;&lt;body&gt;&lt;p&gt;body 元素的内容会显示在浏览器中。&lt;/p&gt;&lt;p&gt;title 元素的内容会显示在浏览器的标题栏中。&lt;/p&gt;&lt;/body&gt;&lt;/html&gt;";

        assertEquals(encodeHtml, EncodeUtils.htmlEncode(html));

        assertEquals(html, EncodeUtils.htmlDecode(encodeHtml).toString());
    }

    @Test
    public void binEncode_binDecode() {
        String test = "test";
        String binary = EncodeUtils.binEncode(test);
        assertEquals("test", EncodeUtils.binDecode(binary));
    }
}