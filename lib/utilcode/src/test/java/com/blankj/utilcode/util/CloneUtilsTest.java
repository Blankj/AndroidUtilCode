package com.blankj.utilcode.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/04/08
 *     desc  : test CloneUtils
 * </pre>
 */
public class CloneUtilsTest {

    @Test
    public void deepClone() {
        Result<Person> result = new Result<>(new Person("Blankj"));
        Result<Person> cloneResult = CloneUtils.deepClone(result, GsonUtils.getType(Result.class, Person.class));
        System.out.println(result);
        System.out.println(cloneResult);
        Assert.assertNotEquals(result, cloneResult);
    }

    static class Result<T> {
        int    code;
        String message;
        T      data;

        Result(T data) {
            this.code = 200;
            this.message = "success";
            this.data = data;
        }

        @Override
        public String toString() {
            return "{\"code\":" + primitive2String(code) +
                    ",\"message\":" + primitive2String(message) +
                    ",\"data\":" + primitive2String(data) + "}";
        }
    }

    static class Person {

        String name;
        int    gender;
        String address;

        Person(String name) {
            this.name = name;
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