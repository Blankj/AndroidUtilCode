package com.blankj.subutil.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

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
    public void deepClone() throws Exception {
        Person person = new Person("Blankj");
        Person clonePerson = CloneUtils.deepClone(person);
        System.out.println(person);
        System.out.println(clonePerson);
        Assert.assertNotEquals(person, clonePerson);
    }

    static class Person implements Serializable {

        String name;
        int    gender;
        String address;

        public Person(String name) {
            this.name = name;
            this.gender = gender;
            this.address = address;
        }

        @Override
        public String toString() {
            return "{\"name\":" + name + ",\"gender\":" + gender + ",\"address\":" + address + "}";
        }
    }
}