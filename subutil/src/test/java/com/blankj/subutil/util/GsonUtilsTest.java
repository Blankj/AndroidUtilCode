package com.blankj.subutil.util;

import org.junit.Assert;
import org.junit.Test;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/26
 *     desc  : test GsonUtils
 * </pre>
 */
public class GsonUtilsTest {

    @Test
    public void getGson() {
        Assert.assertNotNull(GsonUtils.getGson());
        Assert.assertNotNull(GsonUtils.getGson(false));
    }

    @Test
    public void toJson() {
        Person person = new Person("Blankj");
        Assert.assertEquals(
                "{\"name\":\"Blankj\",\"gender\":0,\"address\":null}",
                GsonUtils.toJson(person)
        );
        Assert.assertEquals(
                "{\"name\":\"Blankj\",\"gender\":0}",
                GsonUtils.toJson(person, false)
        );
    }

    @Test
    public void fromJson() {
        Person person = new Person("Blankj");
        Assert.assertEquals(
                person,
                GsonUtils.fromJson("{\"name\":\"Blankj\",\"gender\":0,\"address\":null}", Person.class)
        );
        Assert.assertEquals(
                person,
                GsonUtils.fromJson("{\"name\":\"Blankj\",\"gender\":0}", Person.class)
        );
    }

    static class Person {

        String name;
        int    gender;
        String address;

        public Person(String name) {
            this.name = name;
            this.gender = gender;
            this.address = address;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || !(obj instanceof Person)) return false;
            Person p = (Person) obj;
            return equals(name, p.name) && p.gender == gender && equals(address, p.address);
        }

        private boolean equals(final Object o1, final Object o2) {
            return o1 == o2 || (o1 != null && o1.equals(o2));
        }
    }
}