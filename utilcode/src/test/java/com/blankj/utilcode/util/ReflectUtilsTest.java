package com.blankj.utilcode.util;

import com.blankj.utilcode.util.reflect.PrivateConstructors;
import com.blankj.utilcode.util.reflect.Test1;
import com.blankj.utilcode.util.reflect.Test7;
import com.blankj.utilcode.util.reflect.Test8;
import com.blankj.utilcode.util.reflect.Test2;
import com.blankj.utilcode.util.reflect.Test3;
import com.blankj.utilcode.util.reflect.Test4;
import com.blankj.utilcode.util.reflect.Test5;
import com.blankj.utilcode.util.reflect.Test6;
import com.blankj.utilcode.util.reflect.TestHierarchicalMethodsBase;
import com.blankj.utilcode.util.reflect.TestHierarchicalMethodsSubclass;
import com.blankj.utilcode.util.reflect.TestPrivateStaticFinal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/15
 *     desc  :
 * </pre>
 */
public class ReflectUtilsTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void reflect() throws Exception {
        assertEquals(
                ReflectUtils.reflect(Object.class),
                ReflectUtils.reflect("java.lang.Object", ClassLoader.getSystemClassLoader())
        );
        assertEquals(
                ReflectUtils.reflect(Object.class),
                ReflectUtils.reflect("java.lang.Object")
        );
        assertEquals(
                (Object) ReflectUtils.reflect(String.class).get(),
                ReflectUtils.reflect("java.lang.String").get()
        );
        assertEquals(
                Object.class,
                ReflectUtils.reflect(Object.class).get()
        );
        assertEquals(
                "abc",
                ReflectUtils.reflect((Object) "abc").get()
        );
        assertEquals(
                1,
                (int) ReflectUtils.reflect(1).get()
        );
    }

    @Test
    public void newInstance() throws Exception {
        assertEquals(
                "",
                ReflectUtils.reflect(String.class).newInstance().get()
        );
        assertEquals(
                "abc",
                ReflectUtils.reflect(String.class).newInstance("abc").get()
        );
        assertEquals(
                "abc",
                ReflectUtils.reflect(String.class).newInstance("abc".getBytes()).get()
        );
        assertEquals(
                "abc",
                ReflectUtils.reflect(String.class).newInstance("abc".toCharArray()).get()
        );
        assertEquals(
                "b",
                ReflectUtils.reflect(String.class).newInstance("abc".toCharArray(), 1, 1).get()
        );
    }

    @Test
    public void newInstancePrivate() throws Exception {
        assertNull(ReflectUtils.reflect(PrivateConstructors.class).newInstance().getField("string"));

        assertEquals(
                "abc",
                ReflectUtils.reflect(PrivateConstructors.class).newInstance("abc").getField("string")
        );
    }

    @Test
    public void newInstanceNull() throws Exception {
        Test2 test2 = ReflectUtils.reflect(Test2.class).newInstance((Object) null).get();
        assertNull(test2.n);
    }

    @Test
    public void newInstanceWithPrivate() throws Exception {
        Test7 t1 = ReflectUtils.reflect(Test7.class).newInstance(1).get();
        assertEquals(1, (int) t1.i);
        assertNull(t1.s);

        Test7 t2 = ReflectUtils.reflect(Test7.class).newInstance("a").get();
        assertNull(t2.i);
        assertEquals("a", t2.s);

        Test7 t3 = ReflectUtils.reflect(Test7.class).newInstance("a", 1).get();
        assertEquals(1, (int) t3.i);
        assertEquals("a", t3.s);
    }

    @Test
    public void newInstanceAmbiguity() throws Exception {
        Test2 test;

        test = ReflectUtils.reflect(Test2.class).newInstance().get();
        assertEquals(null, test.n);
        assertEquals(Test2.ConstructorType.NO_ARGS, test.constructorType);

        test = ReflectUtils.reflect(Test2.class).newInstance("abc").get();
        assertEquals("abc", test.n);
        assertEquals(Test2.ConstructorType.OBJECT, test.constructorType);

        test = ReflectUtils.reflect(Test2.class).newInstance(new Long("1")).get();
        assertEquals(1L, test.n);
        assertEquals(Test2.ConstructorType.NUMBER, test.constructorType);

        test = ReflectUtils.reflect(Test2.class).newInstance(1).get();
        assertEquals(1, test.n);
        assertEquals(Test2.ConstructorType.INTEGER, test.constructorType);

        test = ReflectUtils.reflect(Test2.class).newInstance('a').get();
        assertEquals('a', test.n);
        assertEquals(Test2.ConstructorType.OBJECT, test.constructorType);
    }

    @Test
    public void method() throws Exception {
        // instance methods
        assertEquals(
                "",
                ReflectUtils.reflect((Object) " ").method("trim").get()
        );
        assertEquals(
                "12",
                ReflectUtils.reflect((Object) " 12 ").method("trim").get()
        );
        assertEquals(
                "34",
                ReflectUtils.reflect((Object) "1234").method("substring", 2).get()
        );
        assertEquals(
                "12",
                ReflectUtils.reflect((Object) "1234").method("substring", 0, 2).get()
        );
        assertEquals(
                "1234",
                ReflectUtils.reflect((Object) "12").method("concat", "34").get()
        );
        assertEquals(
                "123456",
                ReflectUtils.reflect((Object) "12").method("concat", "34").method("concat", "56").get()
        );
        assertEquals(
                2,
                (int) ReflectUtils.reflect((Object) "1234").method("indexOf", "3").get()
        );
        assertEquals(
                2.0f,
                ReflectUtils.reflect((Object) "1234").method("indexOf", "3").method("floatValue").get(), 0.0f
        );
        assertEquals(
                "2",
                ReflectUtils.reflect((Object) "1234").method("indexOf", "3").method("toString").get()
        );

        // static methods
        assertEquals(
                "true",
                ReflectUtils.reflect(String.class).method("valueOf", true).get()
        );
        assertEquals(
                "1",
                ReflectUtils.reflect(String.class).method("valueOf", 1).get()
        );
        assertEquals(
                "abc",
                ReflectUtils.reflect(String.class).method("valueOf", "abc".toCharArray()).get()
        );
        assertEquals(
                "abc",
                ReflectUtils.reflect(String.class).method("copyValueOf", "abc".toCharArray()).get()
        );
        assertEquals(
                "b",
                ReflectUtils.reflect(String.class).method("copyValueOf", "abc".toCharArray(), 1, 1).get()
        );
    }

    @Test
    public void methodVoid() throws Exception {
        // instance methods
        Test4 test4 = new Test4();
        assertEquals(
                test4,
                ReflectUtils.reflect(test4).method("i_method").get()
        );

        // static methods
        assertEquals(
                Test4.class,
                ReflectUtils.reflect(Test4.class).method("s_method").get()
        );
    }

    @Test
    public void methodPrivate() throws Exception {
        // instance methods
        Test5 test8 = new Test5();
        assertEquals(
                test8,
                ReflectUtils.reflect(test8).method("i_method").get()
        );

        // static methods
        assertEquals(
                Test5.class,
                ReflectUtils.reflect(Test5.class).method("s_method").get()
        );
    }

    @Test
    public void methodNullArguments() throws Exception {
        Test6 test9 = new Test6();
        ReflectUtils.reflect(test9).method("put", "key", "value");
        assertTrue(test9.map.containsKey("key"));
        assertEquals("value", test9.map.get("key"));

        ReflectUtils.reflect(test9).method("put", "key", null);
        assertTrue(test9.map.containsKey("key"));
        assertNull(test9.map.get("key"));
    }

    @Test
    public void methodSuper() throws Exception {
        TestHierarchicalMethodsSubclass subclass = new TestHierarchicalMethodsSubclass();
        assertEquals(
                TestHierarchicalMethodsBase.PUBLIC_RESULT,
                ReflectUtils.reflect(subclass).method("pub_base_method", 1).get()
        );

        assertEquals(
                TestHierarchicalMethodsBase.PRIVATE_RESULT,
                ReflectUtils.reflect(subclass).method("very_priv_method").get()
        );
    }

    @Test
    public void methodDeclaring() throws Exception {
        TestHierarchicalMethodsSubclass subclass = new TestHierarchicalMethodsSubclass();
        assertEquals(
                TestHierarchicalMethodsSubclass.PRIVATE_RESULT,
                ReflectUtils.reflect(subclass).method("priv_method", 1).get()
        );

        TestHierarchicalMethodsBase baseClass = new TestHierarchicalMethodsBase();
        assertEquals(
                TestHierarchicalMethodsBase.PRIVATE_RESULT,
                ReflectUtils.reflect(baseClass).method("priv_method", 1).get()
        );
    }

    @Test
    public void methodAmbiguity() throws Exception {
        Test3 test;

        test = ReflectUtils.reflect(Test3.class).newInstance().method("method").get();
        assertEquals(null, test.n);
        assertEquals(Test3.MethodType.NO_ARGS, test.methodType);

        test = ReflectUtils.reflect(Test3.class).newInstance().method("method", "abc").get();
        assertEquals("abc", test.n);
        assertEquals(Test3.MethodType.OBJECT, test.methodType);

        test = ReflectUtils.reflect(Test3.class).newInstance().method("method", new Long("1")).get();
        assertEquals(1L, test.n);
        assertEquals(Test3.MethodType.NUMBER, test.methodType);

        test = ReflectUtils.reflect(Test3.class).newInstance().method("method", 1).get();
        assertEquals(1, test.n);
        assertEquals(Test3.MethodType.INTEGER, test.methodType);

        test = ReflectUtils.reflect(Test3.class).newInstance().method("method", 'a').get();
        assertEquals('a', test.n);
        assertEquals(Test3.MethodType.OBJECT, test.methodType);
    }

    @Test
    public void field() throws Exception {
        // instance methods
        Test1 test1 = new Test1();
        ReflectUtils.reflect(test1).field("I_INT1", 1);
        assertEquals(1, (int) ReflectUtils.reflect(test1).getField("I_INT1"));

        ReflectUtils.reflect(test1).field("I_INT2", 1);
        assertEquals(1, (int) ReflectUtils.reflect(test1).getField("I_INT2"));

        ReflectUtils.reflect(test1).field("I_INT2", null);
        assertNull(ReflectUtils.reflect(test1).getField("I_INT2"));

        // static methods
        ReflectUtils.reflect(Test1.class).field("S_INT1", 1);
        assertEquals(1, (int) ReflectUtils.reflect(Test1.class).getField("S_INT1"));

        ReflectUtils.reflect(Test1.class).field("S_INT2", 1);
        assertEquals(1, (int) ReflectUtils.reflect(Test1.class).getField("S_INT2"));

        ReflectUtils.reflect(Test1.class).field("S_INT2", null);
        assertNull(ReflectUtils.reflect(Test1.class).getField("S_INT2"));

        // hierarchies
        TestHierarchicalMethodsSubclass test2 = new TestHierarchicalMethodsSubclass();

        ReflectUtils.reflect(test2).field("invisibleField1", 1);
        assertEquals(1, (int) ReflectUtils.reflect(test2).getField("invisibleField1"));

        ReflectUtils.reflect(test2).field("invisibleField2", 1);
        assertEquals(1, (int) ReflectUtils.reflect(test2).getField("invisibleField2"));

        ReflectUtils.reflect(test2).field("invisibleField3", 1);
        assertEquals(1, (int) ReflectUtils.reflect(test2).getField("invisibleField3"));

        ReflectUtils.reflect(test2).field("visibleField1", 1);
        assertEquals(1, (int) ReflectUtils.reflect(test2).getField("visibleField1"));

        ReflectUtils.reflect(test2).field("visibleField2", 1);
        assertEquals(1, (int) ReflectUtils.reflect(test2).getField("visibleField2"));

        ReflectUtils.reflect(test2).field("visibleField3", 1);
        assertEquals(1, (int) ReflectUtils.reflect(test2).getField("visibleField3"));
    }

    @Test
    public void fieldFinal() throws Exception {
        // instance methods
        Test8 test11 = new Test8();
        ReflectUtils.reflect(test11).field("F_INT1", 1);
        assertEquals(1, (int) (Integer) ReflectUtils.reflect(test11).getField("F_INT1"));

        ReflectUtils.reflect(test11).field("F_INT2", 1);
        assertEquals(1, (int) (Integer) ReflectUtils.reflect(test11).getField("F_INT2"));

        ReflectUtils.reflect(test11).field("F_INT2", null);
        assertNull(ReflectUtils.reflect(test11).getField("F_INT2"));

        // static methods
        ReflectUtils.reflect(Test8.class).field("SF_INT1", 1);
        assertEquals(1, (int) (Integer) ReflectUtils.reflect(Test8.class).getField("SF_INT1"));

        ReflectUtils.reflect(Test8.class).field("SF_INT2", 1);
        assertEquals(1, (int) (Integer) ReflectUtils.reflect(Test8.class).getField("SF_INT2"));

        ReflectUtils.reflect(Test8.class).field("SF_INT2", null);
        assertNull(ReflectUtils.reflect(Test8.class).getField("SF_INT2"));
    }

    @Test
    public void fieldAdvanced() throws Exception {
        ReflectUtils.reflect(Test1.class)
                .field("S_DATA", ReflectUtils.reflect(Test1.class).newInstance())
                .field("S_DATA")
                .field("I_DATA", ReflectUtils.reflect(Test1.class).newInstance())
                .field("I_DATA")
                .field("I_INT1", 1)
                .field("S_INT1", 2);
        assertEquals(2, Test1.S_INT1);
        assertEquals(null, Test1.S_INT2);
        assertEquals(0, Test1.S_DATA.I_INT1);
        assertEquals(null, Test1.S_DATA.I_INT2);
        assertEquals(1, Test1.S_DATA.I_DATA.I_INT1);
        assertEquals(null, Test1.S_DATA.I_DATA.I_INT2);
    }

    @Test
    public void fieldFinalAdvanced() throws Exception {
        ReflectUtils.reflect(Test8.class)
                .field("S_DATA", ReflectUtils.reflect(Test8.class).newInstance())
                .field("S_DATA")
                .field("I_DATA", ReflectUtils.reflect(Test8.class).newInstance())
                .field("I_DATA")
                .field("F_INT1", 1)
                .field("F_INT2", 1)
                .field("SF_INT1", 2)
                .field("SF_INT2", 2);
        assertEquals(2, Test8.SF_INT1);
        assertEquals(new Integer(2), Test8.SF_INT2);
        assertEquals(0, Test8.S_DATA.F_INT1);
        assertEquals(new Integer(0), Test8.S_DATA.F_INT2);
        assertEquals(1, Test8.S_DATA.I_DATA.F_INT1);
        assertEquals(new Integer(1), Test8.S_DATA.I_DATA.F_INT2);
    }

    @Test
    public void fieldPrivateStaticFinal() throws Exception {
        ReflectUtils.reflect(TestPrivateStaticFinal.class).field("I1", 2);
        ReflectUtils.reflect(TestPrivateStaticFinal.class).field("I2", 2);

        assertEquals(2, (int) ReflectUtils.reflect(TestPrivateStaticFinal.class).getField("I1"));
        assertEquals(2, (int) ReflectUtils.reflect(TestPrivateStaticFinal.class).getField("I2"));
    }

    @Test
    public void fieldPrivate() throws Exception {
        class Foo {
            private String bar;
        }
        Foo foo = new Foo();
        ReflectUtils.reflect(foo).field("bar", "FooBar");
        assertThat(foo.bar, is("FooBar"));
        assertEquals("FooBar", ReflectUtils.reflect(foo).getField("bar"));

        ReflectUtils.reflect(foo).field("bar", null);
        assertNull(foo.bar);
        assertNull(ReflectUtils.reflect(foo).getField("bar"));
    }

    @Test
    public void _hashCode() throws Exception {
        Object object = new Object();
        assertEquals(ReflectUtils.reflect(object).hashCode(), object.hashCode());
    }

    @Test
    public void _toString() throws Exception {
        Object object = new Object() {
            @Override
            public String toString() {
                return "test";
            }
        };
        assertEquals(ReflectUtils.reflect(object).toString(), object.toString());
    }

    @Test
    public void _equals() {
        Object object = new Object();
        ReflectUtils a = ReflectUtils.reflect(object);
        ReflectUtils b = ReflectUtils.reflect(object);
        ReflectUtils c = ReflectUtils.reflect(object);

        assertTrue(b.equals(a));
        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));
        //noinspection ObjectEqualsNull
        assertFalse(a.equals(null));
    }
}