package com.blankj.subutil.util;

import android.support.annotation.NonNull;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/08/07
 *     desc  : 反射相关工具类
 * </pre>
 */
public final class ReflectUtils {

    private final Class<?> type;

    private final Object object;

    private ReflectUtils(Class<?> type) {
        this(type, type);
    }

    private ReflectUtils(Class<?> type, Object object) {
        this.type = type;
        this.object = object;
    }

    public static ReflectUtils init(@NonNull final String name) {
        return init(forName(name));
    }

    public static ReflectUtils init(@NonNull final String name, @NonNull final ClassLoader classLoader) {
        return init(forName(name, classLoader));
    }

    public static ReflectUtils init(@NonNull final Class<?> clazz) {
        return new ReflectUtils(clazz);
    }

    public static ReflectUtils init(@NonNull final Object object) {
        return new ReflectUtils(object.getClass(), object);
    }

    private static ReflectUtils init(Class<?> type, Object object) {
        return new ReflectUtils(type, object);
    }

    public static <T extends AccessibleObject> T accessible(@NonNull final T accessible) {
        if (accessible instanceof Member) {
            Member member = (Member) accessible;
            if (Modifier.isPublic(member.getModifiers())
                    && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
                return accessible;
            }
        }
        if (!accessible.isAccessible()) {
            accessible.setAccessible(true);
        }
        return accessible;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Members
    ///////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) object;
    }

    public ReflectUtils set(String name, Object value) {
        try {
            Field field = field0(name);
            if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            }
            field.set(object, unwrap(value));
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T get(String name) {
        return field(name).<T>get();
    }

    public ReflectUtils field(String name) {
        try {
            Field field = field0(name);
            return init(field.getType(), field.get(object));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Field field0(String name) {
        Class<?> t = type();
        try {
            return accessible(t.getField(name));
        } catch (NoSuchFieldException e) {
            do {
                try {
                    return accessible(t.getDeclaredField(name));
                } catch (NoSuchFieldException ignore) {
                }

                t = t.getSuperclass();
            }
            while (t != null);
            throw new RuntimeException();
        }
    }

    /**
     * Get a Map containing field names and wrapped values for the fields'
     * values.
     * <p>
     * If the wrapped object is a {@link Class}, then this will return static
     * fields. If the wrapped object is any other {@link Object}, then this will
     * return instance fields.
     * <p>
     * These two calls are equivalent <code><pre>
     * init(object).field("myField");
     * init(object).fields().get("myField");
     * </pre></code>
     *
     * @return A map containing field names and wrapped values.
     */
    public Map<String, ReflectUtils> fields() {
        Map<String, ReflectUtils> result = new LinkedHashMap<String, ReflectUtils>();
        Class<?> t = type();

        do {
            for (Field field : t.getDeclaredFields()) {
                if (type != object ^ Modifier.isStatic(field.getModifiers())) {
                    String name = field.getName();

                    if (!result.containsKey(name))
                        result.put(name, field(name));
                }
            }

            t = t.getSuperclass();
        }
        while (t != null);

        return result;
    }

    /**
     * Call a method by its name.
     * <p>
     * This is a convenience method for calling
     * <code>call(name, new Object[0])</code>
     *
     * @param name The method name
     * @return The wrapped method result or the same wrapped object if the
     * method returns <code>void</code>, to be used for further
     * reflection.
     * @throws RuntimeException If any reflection exception occurred.
     * @see #call(String, Object...)
     */
    public ReflectUtils call(String name) {
        return call(name, new Object[0]);
    }

    /**
     * Call a method by its name.
     * <p>
     * This is roughly equivalent to {@link Method#invoke(Object, Object...)}.
     * If the wrapped object is a {@link Class}, then this will invoke a static
     * method. If the wrapped object is any other {@link Object}, then this will
     * invoke an instance method.
     * <p>
     * Just like {@link Method#invoke(Object, Object...)}, this will try to wrap
     * primitive types or unwrap primitive type wrappers if applicable. If
     * several methods are applicable, by that rule, the first one encountered
     * is called. i.e. when calling <code><pre>
     * init(...).call("method", 1, 1);
     * </pre></code> The first of the following methods will be called:
     * <code><pre>
     * public void method(int param1, Integer param2);
     * public void method(Integer param1, int param2);
     * public void method(Number param1, Number param2);
     * public void method(Number param1, Object param2);
     * public void method(int param1, Object param2);
     * </pre></code>
     * <p>
     * The best matching method is searched for with the following strategy:
     * <ol>
     * <li>public method with exact signature match in class hierarchy</li>
     * <li>non-public method with exact signature match init declaring class</li>
     * <li>public method with similar signature in class hierarchy</li>
     * <li>non-public method with similar signature init declaring class</li>
     * </ol>
     *
     * @param name The method name
     * @param args The method arguments
     * @return The wrapped method result or the same wrapped object if the
     * method returns <code>void</code>, to be used for further
     * reflection.
     * @throws RuntimeException If any reflection exception occurred.
     */
    public ReflectUtils call(String name, Object... args) {
        Class<?>[] types = types(args);

        // Try invoking the "canonical" method, i.e. the one with exact
        // matching argument types
        try {
            Method method = exactMethod(name, types);
            return init(method, object, args);
        }

        // If there is no exact match, try to find a method that has a "similar"
        // signature if primitive argument types are converted to their wrappers
        catch (NoSuchMethodException e) {
            try {
                Method method = similarMethod(name, types);
                return init(method, object, args);
            } catch (NoSuchMethodException e1) {
                throw new RuntimeException(e1);
            }
        }
    }

    /**
     * Searches a method with the exact same signature as desired.
     * <p>
     * If a public method is found in the class hierarchy, this method is returned.
     * Otherwise a private method with the exact same signature is returned.
     * If no exact match could be found, we let the {@code NoSuchMethodException} pass through.
     */
    private Method exactMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        Class<?> t = type();

        // first priority: find a public method with exact signature match in class hierarchy
        try {
            return t.getMethod(name, types);
        }

        // second priority: find a private method with exact signature match init declaring class
        catch (NoSuchMethodException e) {
            do {
                try {
                    return t.getDeclaredMethod(name, types);
                } catch (NoSuchMethodException ignore) {
                }

                t = t.getSuperclass();
            }
            while (t != null);

            throw new NoSuchMethodException();
        }
    }

    /**
     * Searches a method with a similar signature as desired using
     * {@link #isSimilarSignature(java.lang.reflect.Method, String, Class[])}.
     * <p>
     * First public methods are searched in the class hierarchy, then private
     * methods init the declaring class. If a method could be found, it is
     * returned, otherwise a {@code NoSuchMethodException} is thrown.
     */
    private Method similarMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        Class<?> t = type();

        // first priority: find a public method with a "similar" signature in class hierarchy
        // similar interpreted in when primitive argument types are converted to their wrappers
        for (Method method : t.getMethods()) {
            if (isSimilarSignature(method, name, types)) {
                return method;
            }
        }

        // second priority: find a non-public method with a "similar" signature init declaring class
        do {
            for (Method method : t.getDeclaredMethods()) {
                if (isSimilarSignature(method, name, types)) {
                    return method;
                }
            }

            t = t.getSuperclass();
        }
        while (t != null);

        throw new NoSuchMethodException("No similar method " + name + " with params " + Arrays.toString(types) + " could be found init type " + type() + ".");
    }

    /**
     * Determines if a method has a "similar" signature, especially if wrapping
     * primitive argument types would result in an exactly matching signature.
     */
    private boolean isSimilarSignature(Method possiblyMatchingMethod, String desiredMethodName, Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName) && match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    /**
     * Call a constructor.
     * <p>
     * This is a convenience method for calling
     * <code>create(new Object[0])</code>
     *
     * @return The wrapped new object, to be used for further reflection.
     * @throws RuntimeException If any reflection exception occurred.
     * @see #create(Object...)
     */
    public ReflectUtils create() {
        return create(new Object[0]);
    }

    /**
     * Call a constructor.
     * <p>
     * This is roughly equivalent to {@link Constructor#newInstance(Object...)}.
     * If the wrapped object is a {@link Class}, then this will create a new
     * object of that class. If the wrapped object is any other {@link Object},
     * then this will create a new object of the same type.
     * <p>
     * Just like {@link Constructor#newInstance(Object...)}, this will try to
     * wrap primitive types or unwrap primitive type wrappers if applicable. If
     * several constructors are applicable, by that rule, the first one
     * encountered is called. i.e. when calling <code><pre>
     * init(C.class).create(1, 1);
     * </pre></code> The first of the following constructors will be applied:
     * <code><pre>
     * public C(int param1, Integer param2);
     * public C(Integer param1, int param2);
     * public C(Number param1, Number param2);
     * public C(Number param1, Object param2);
     * public C(int param1, Object param2);
     * </pre></code>
     *
     * @param args The constructor arguments
     * @return The wrapped new object, to be used for further reflection.
     * @throws RuntimeException If any reflection exception occurred.
     */
    public ReflectUtils create(Object... args) {
        Class<?>[] types = types(args);

        // Try invoking the "canonical" constructor, i.e. the one with exact
        // matching argument types
        try {
            Constructor<?> constructor = type().getDeclaredConstructor(types);
            return init(constructor, args);
        }

        // If there is no exact match, try to find one that has a "similar"
        // signature if primitive argument types are converted to their wrappers
        catch (NoSuchMethodException e) {
            for (Constructor<?> constructor : type().getDeclaredConstructors()) {
                if (match(constructor.getParameterTypes(), types)) {
                    return init(constructor, args);
                }
            }

            throw new RuntimeException(e);
        }
    }

    /**
     * Create a proxy for the wrapped object allowing to typesafely invoke
     * methods init it using a custom interface
     *
     * @param proxyType The interface type that is implemented by the proxy
     * @return A proxy for the wrapped object
     */
    @SuppressWarnings("unchecked")
    public <P> P as(final Class<P> proxyType) {
        final boolean isMap = (object instanceof Map);
        final InvocationHandler handler = new InvocationHandler() {
            @SuppressWarnings("null")
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();

                // Actual method name matches always come first
                try {
                    return init(type, object).call(name, args).get();
                }

                // [#14] Emulate POJO behaviour init wrapped map objects
                catch (RuntimeException e) {
                    if (isMap) {
                        Map<String, Object> map = (Map<String, Object>) object;
                        int length = (args == null ? 0 : args.length);

                        if (length == 0 && name.startsWith("get")) {
                            return map.get(property(name.substring(3)));
                        } else if (length == 0 && name.startsWith("is")) {
                            return map.get(property(name.substring(2)));
                        } else if (length == 1 && name.startsWith("set")) {
                            map.put(property(name.substring(3)), args[0]);
                            return null;
                        }
                    }
                    throw e;
                }
            }
        };

        return (P) Proxy.newProxyInstance(proxyType.getClassLoader(), new Class[]{proxyType}, handler);
    }

    /**
     * Get the POJO property name of an getter/setter
     */
    private static String property(String string) {
        int length = string.length();

        if (length == 0) {
            return "";
        } else if (length == 1) {
            return string.toLowerCase();
        } else {
            return string.substring(0, 1).toLowerCase() + string.substring(1);
        }
    }

    // ---------------------------------------------------------------------
    // Object API
    // ---------------------------------------------------------------------

    /**
     * Check whether two arrays of types match, converting primitive types to
     * their corresponding wrappers.
     */
    private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
        if (declaredTypes.length == actualTypes.length) {
            for (int i = 0; i < actualTypes.length; i++) {
                if (actualTypes[i] == NULL.class)
                    continue;

                if (wrapper(declaredTypes[i]).isAssignableFrom(wrapper(actualTypes[i])))
                    continue;

                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return object.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReflectUtils) {
            return object.equals(((ReflectUtils) obj).get());
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return object.toString();
    }

    // ---------------------------------------------------------------------
    // Utility methods
    // ---------------------------------------------------------------------

    /**
     * Wrap an object created from a constructor
     */
    private static ReflectUtils init(Constructor<?> constructor, Object... args) {
        try {
            return init(constructor.getDeclaringClass(), accessible(constructor).newInstance(args));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Wrap an object returned from a method
     */
    private static ReflectUtils init(Method method, Object object, Object... args) {
        try {
            accessible(method);

            if (method.getReturnType() == void.class) {
                method.invoke(object, args);
                return init(object);
            } else {
                return init(method.invoke(object, args));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Unwrap an object
     */
    private static Object unwrap(Object object) {
        if (object instanceof ReflectUtils) {
            return ((ReflectUtils) object).get();
        }

        return object;
    }

    /**
     * Get an array of types for an array of objects
     *
     * @see Object#getClass()
     */
    private static Class<?>[] types(@NonNull final Object... values) {
        Class<?>[] result = new Class[values.length];
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            result[i] = value == null ? NULL.class : value.getClass();
        }
        return result;
    }

    public Class<?> type() {
        return type;
    }

    /**
     * Get a wrapper type for a primitive type, or the argument type itself, if
     * it is not a primitive type.
     */
    public static Class<?> wrapper(Class<?> type) {
        if (type == null) {
            return null;
        } else if (type.isPrimitive()) {
            if (boolean.class == type) {
                return Boolean.class;
            } else if (int.class == type) {
                return Integer.class;
            } else if (long.class == type) {
                return Long.class;
            } else if (short.class == type) {
                return Short.class;
            } else if (byte.class == type) {
                return Byte.class;
            } else if (double.class == type) {
                return Double.class;
            } else if (float.class == type) {
                return Float.class;
            } else if (char.class == type) {
                return Character.class;
            } else if (void.class == type) {
                return Void.class;
            }
        }

        return type;
    }

    private static class NULL {
    }


    private static Class<?> forName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> forName(String name, ClassLoader classLoader) {
        try {
            return Class.forName(name, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}