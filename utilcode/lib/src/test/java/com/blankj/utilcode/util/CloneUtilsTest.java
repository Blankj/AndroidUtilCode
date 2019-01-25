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
            return "{\"code\":" + code + ",\"message\":" + message + ",\"data\":" + data + "}";
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
            return "{\"name\":" + name + ",\"gender\":" + gender + ",\"address\":" + address + "}";
        }
    }
}