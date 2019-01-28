package com.blankj.utilcode.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/26
 *     desc  : test LogUtils
 * </pre>
 */
public class LogUtilsTest extends BaseTest {

    private static final String              JSON        = "\r\n{\"tools\": [{ \"name\":\"css format\" , \"site\":\"http://tools.w3cschool.cn/code/css\" },{ \"name\":\"JSON format\" , \"site\":\"http://tools.w3cschool.cn/code/JSON\" },{ \"name\":\"pwd check\" , \"site\":\"http://tools.w3cschool.cn/password/my_password_safe\" }]}";
    private static final String              XML         = "<books><book><author>Jack Herrington</author><title>PHP Hacks</title><publisher>O'Reilly</publisher></book><book><author>Jack Herrington</author><title>Podcasting Hacks</title><publisher>O'Reilly</publisher></book></books>";
    private static final int[]               ONE_D_ARRAY = new int[]{1, 2, 3};
    private static final int[][]             TWO_D_ARRAY = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    private static final ArrayList<String>   LIST        = new ArrayList<>();
    private static final Map<String, Object> MAP         = new HashMap<>();

    @Test
    public void testV() {
        LogUtils.v();
        LogUtils.v("");
        LogUtils.v(null);
        LogUtils.v("hello");
        LogUtils.v("hello\nworld");
        LogUtils.v("hello", "world");
    }

    @Test
    public void testVTag() {
        LogUtils.vTag("");
        LogUtils.vTag("", "");
        LogUtils.vTag("TAG", null);
        LogUtils.vTag("TAG", "hello");
        LogUtils.vTag("TAG", "hello\nworld");
        LogUtils.vTag("TAG", "hello", "world");
    }

    @Test
    public void testD() {
        LogUtils.d();
        LogUtils.d("");
        LogUtils.d(null);
        LogUtils.d("hello");
        LogUtils.d("hello\nworld");
        LogUtils.d("hello", "world");
    }

    @Test
    public void testDTag() {
        LogUtils.dTag("");
        LogUtils.dTag("", "");
        LogUtils.dTag("TAG", null);
        LogUtils.dTag("TAG", "hello");
        LogUtils.dTag("TAG", "hello\nworld");
        LogUtils.dTag("TAG", "hello", "world");
    }

    @Test
    public void testI() {
        LogUtils.i();
        LogUtils.i("");
        LogUtils.i(null);
        LogUtils.i("hello");
        LogUtils.i("hello\nworld");
        LogUtils.i("hello", "world");
    }

    @Test
    public void testITag() {
        LogUtils.iTag("");
        LogUtils.iTag("", "");
        LogUtils.iTag("TAG", null);
        LogUtils.iTag("TAG", "hello");
        LogUtils.iTag("TAG", "hello\nworld");
        LogUtils.iTag("TAG", "hello", "world");
    }

    @Test
    public void testW() {
        LogUtils.w();
        LogUtils.w("");
        LogUtils.w(null);
        LogUtils.w("hello");
        LogUtils.w("hello\nworld");
        LogUtils.w("hello", "world");
    }

    @Test
    public void testWTag() {
        LogUtils.wTag("");
        LogUtils.wTag("", "");
        LogUtils.wTag("TAG", null);
        LogUtils.wTag("TAG", "hello");
        LogUtils.wTag("TAG", "hello\nworld");
        LogUtils.wTag("TAG", "hello", "world");
    }

    @Test
    public void testE() {
        LogUtils.e();
        LogUtils.e("");
        LogUtils.e(null);
        LogUtils.e("hello");
        LogUtils.e("hello\nworld");
        LogUtils.e("hello", "world");
    }

    @Test
    public void testETag() {
        LogUtils.eTag("");
        LogUtils.eTag("", "");
        LogUtils.eTag("TAG", null);
        LogUtils.eTag("TAG", "hello");
        LogUtils.eTag("TAG", "hello\nworld");
        LogUtils.eTag("TAG", "hello", "world");
    }

    @Test
    public void testA() {
        LogUtils.a();
        LogUtils.a("");
        LogUtils.a(null);
        LogUtils.a("hello");
        LogUtils.a("hello\nworld");
        LogUtils.a("hello", "world");
    }

    @Test
    public void testATag() {
        LogUtils.aTag("");
        LogUtils.aTag("", "");
        LogUtils.aTag("TAG", null);
        LogUtils.aTag("TAG", "hello");
        LogUtils.aTag("TAG", "hello\nworld");
        LogUtils.aTag("TAG", "hello", "world");
    }

    @Test
    public void testJson() {
        LogUtils.json(JSON);
        LogUtils.json(new Person("Blankj"));
    }

    @Test
    public void testXml() {
        LogUtils.xml(XML);
    }

    @Test
    public void testObject() {
        LIST.add("hello");
        LIST.add("log");
        LIST.add("utils");

        MAP.put("name", "AndroidUtilCode");
        MAP.put("class", "LogUtils");
        LogUtils.d((Object) ONE_D_ARRAY);
        LogUtils.d((Object) TWO_D_ARRAY);
        LogUtils.d(LIST);
        LogUtils.d(MAP);
    }

    static class Person {

        String name;
        int    gender;
        String address;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (!(obj instanceof Person)) return false;
            Person p = (Person) obj;
            return equals(name, p.name) && p.gender == gender && equals(address, p.address);
        }

        private static boolean equals(final Object o1, final Object o2) {
            return o1 == o2 || (o1 != null && o1.equals(o2));
        }

        @Override
        public String toString() {
            return "{\"name\":" + primitive2String(name) +
                    ",\"gender\":" + primitive2String(gender) +
                    ",\"address\":" + primitive2String(address) + "}";
        }
    }

    private static String primitive2String(final Object obj) {
        if (obj == null) return "null";
        if (obj instanceof CharSequence) return "\"" + obj.toString() + "\"";
        return obj.toString();
    }
}
