package com.blankj.utilcode.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/26
 *     desc  : test GsonUtils
 * </pre>
 */
public class GsonUtilsTest extends BaseTest {

    @Test
    public void getGson() {
        Assert.assertNotNull(GsonUtils.getGson());
        Assert.assertNotNull(GsonUtils.getGson(false));
    }

    @Test
    public void toJson() {
        Result<Person> result = new Result<>(new Person("Blankj"));
        Assert.assertEquals(
                "{\"code\":200,\"message\":\"success\",\"data\":{\"name\":\"Blankj\",\"gender\":0,\"address\":null}}",
                GsonUtils.toJson(result)
        );
        Assert.assertEquals(
                "{\"code\":200,\"message\":\"success\",\"data\":{\"name\":\"Blankj\",\"gender\":0}}",
                GsonUtils.toJson(result, false)
        );
    }

    @Test
    public void fromJson() {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Blankj"));
        people.add(new Person("Ming"));
        Result<List<Person>> result = new Result<>(people);

        Assert.assertEquals(
                GsonUtils.toJson(result),
                GsonUtils.toJson(
                        GsonUtils.fromJson(
                                GsonUtils.toJson(result),
                                GsonUtils.getType(Result.class, GsonUtils.getListType(Person.class))
                        )
                )
        );
    }

    @Test
    public void getType() {
        Assert.assertEquals(
                "java.util.List<java.lang.String>",
                GsonUtils.getListType(String.class).toString()
        );
        Assert.assertEquals(
                "java.util.Map<java.lang.String, java.lang.Integer>",
                GsonUtils.getMapType(String.class, Integer.class).toString()
        );
        Assert.assertEquals(
                "java.lang.String[]",
                GsonUtils.getArrayType(String.class).toString()
        );
        Assert.assertEquals(
                "com.blankj.utilcode.util.GsonUtilsTest$Result<java.lang.String>",
                GsonUtils.getType(Result.class, String.class).toString()
        );
        Assert.assertEquals(
                "java.util.Map<java.lang.String, java.util.List<java.lang.String>>",
                GsonUtils.getMapType(String.class, GsonUtils.getListType(String.class)).toString()
        );
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
    }

    static class Person {

        String name;
        int    gender;
        String address;

        Person(String name) {
            this.name = name;
        }
    }
}