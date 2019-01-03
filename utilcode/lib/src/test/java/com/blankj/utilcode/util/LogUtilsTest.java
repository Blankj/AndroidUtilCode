package com.blankj.utilcode.util;

import org.junit.Test;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/26
 *     desc  : test LogUtils
 * </pre>
 */
public class LogUtilsTest extends BaseTest {

    private static final String JSON = "{\"tools\": [{ \"name\":\"css format\" , \"site\":\"http://tools.w3cschool.cn/code/css\" },{ \"name\":\"JSON format\" , \"site\":\"http://tools.w3cschool.cn/code/JSON\" },{ \"name\":\"pwd check\" , \"site\":\"http://tools.w3cschool.cn/password/my_password_safe\" }]}";

    @Test
    public void testV() {
        LogUtils.v("");
        LogUtils.v(null);
        LogUtils.v("hello");
        LogUtils.v("hello\nworld");
        LogUtils.v("hello", "world");
    }

    @Test
    public void testVTag() {
        LogUtils.vTag("", "");
        LogUtils.vTag("TAG", null);
        LogUtils.vTag("TAG", "hello");
        LogUtils.vTag("TAG", "hello\nworld");
        LogUtils.vTag("TAG", "hello", "world");
    }

    @Test
    public void testD() {
        LogUtils.d("");
        LogUtils.d(null);
        LogUtils.d("hello");
        LogUtils.d("hello\nworld");
        LogUtils.d("hello", "world");
    }

    @Test
    public void testDTag() {
        LogUtils.dTag("", "");
        LogUtils.dTag("TAG", null);
        LogUtils.dTag("TAG", "hello");
        LogUtils.dTag("TAG", "hello\nworld");
        LogUtils.dTag("TAG", "hello", "world");
    }

    @Test
    public void testI() {
        LogUtils.i("");
        LogUtils.i(null);
        LogUtils.i("hello");
        LogUtils.i("hello\nworld");
        LogUtils.i("hello", "world");
    }

    @Test
    public void testITag() {
        LogUtils.iTag("", "");
        LogUtils.iTag("TAG", null);
        LogUtils.iTag("TAG", "hello");
        LogUtils.iTag("TAG", "hello\nworld");
        LogUtils.iTag("TAG", "hello", "world");
    }

    @Test
    public void testW() {
        LogUtils.w("");
        LogUtils.w(null);
        LogUtils.w("hello");
        LogUtils.w("hello\nworld");
        LogUtils.w("hello", "world");
    }

    @Test
    public void testWTag() {
        LogUtils.wTag("", "");
        LogUtils.wTag("TAG", null);
        LogUtils.wTag("TAG", "hello");
        LogUtils.wTag("TAG", "hello\nworld");
        LogUtils.wTag("TAG", "hello", "world");
    }

    @Test
    public void testE() {
        LogUtils.e("");
        LogUtils.e(null);
        LogUtils.e("hello");
        LogUtils.e("hello\nworld");
        LogUtils.e("hello", "world");
    }

    @Test
    public void testETag() {
        LogUtils.eTag("", "");
        LogUtils.eTag("TAG", null);
        LogUtils.eTag("TAG", "hello");
        LogUtils.eTag("TAG", "hello\nworld");
        LogUtils.eTag("TAG", "hello", "world");
    }

    @Test
    public void testA() {
        LogUtils.a("");
        LogUtils.a(null);
        LogUtils.a("hello");
        LogUtils.a("hello\nworld");
        LogUtils.a("hello", "world");
    }

    @Test
    public void testATag() {
        LogUtils.aTag("", "");
        LogUtils.aTag("TAG", null);
        LogUtils.aTag("TAG", "hello");
        LogUtils.aTag("TAG", "hello\nworld");
        LogUtils.aTag("TAG", "hello", "world");
    }

    @Test
    public void testJson() {
//        LogUtils.json(JSON);
        LogUtils.json(new Person("B\nlankj"));
    }

    @Test
    public void testXml() {
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
    }
}
