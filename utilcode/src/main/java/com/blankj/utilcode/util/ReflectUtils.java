package com.blankj.utilcode.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/15
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

    ///////////////////////////////////////////////////////////////////////////
    // reflect
    ///////////////////////////////////////////////////////////////////////////

    public static ReflectUtils reflect(String className) {
        return reflect(forName(className));
    }

    public static ReflectUtils reflect(String name, ClassLoader classLoader) {
        return reflect(forName(name, classLoader));
    }

    public static ReflectUtils reflect(Class<?> clazz) {
        return new ReflectUtils(clazz);
    }

    public static ReflectUtils reflect(Object object) {
        return new ReflectUtils(object == null ? Object.class : object.getClass(), object);
    }

    private ReflectUtils reflect(Class<?> type, Object object) {
        return new ReflectUtils(type, object);
    }

    private ReflectUtils reflect(Constructor<?> constructor, Object... args) {
        try {
            return new ReflectUtils(constructor.getDeclaringClass(), accessible(constructor).newInstance(args));
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    private static Class<?> forName(String name, ClassLoader classLoader) {
        try {
            return Class.forName(name, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) object;
    }

    public ReflectUtils field(String name) {
        try {
            Field field = field0(name);
            return reflect(field.getType(), field.get(object));
        } catch (IllegalAccessException e) {
            throw new ReflectException(e);
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
            throw new ReflectException(e);
        }
    }

    public ReflectUtils create() {
        return create(new Object[0]);
    }

    public ReflectUtils create(Object... args) {
        Class<?>[] types = types(args);
        try {
            Constructor<?> constructor = type().getDeclaredConstructor(types);
            return reflect(constructor, args);
        } catch (NoSuchMethodException e) {
            for (Constructor<?> constructor : type().getDeclaredConstructors()) {
                if (match(constructor.getParameterTypes(), types)) {
                    return reflect(constructor, args);
                }
            }
            throw new ReflectException(e);
        }
    }

    private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
        if (declaredTypes.length == actualTypes.length) {
            for (int i = 0; i < actualTypes.length; i++) {
                if (actualTypes[i] == NULL.class
                        || wrapper(declaredTypes[i]).isAssignableFrom(wrapper(actualTypes[i]))) {
                    continue;
                }
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private static Class<?>[] types(Object... values) {
        if (values == null) return new Class[0];

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


    @Override
    public int hashCode() {
        return object.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ReflectUtils && object.equals(((ReflectUtils) obj).get());
    }

    @Override
    public String toString() {
        return object.toString();
    }


    /**
     * @param accessible
     * @param <T>
     * @return
     */
    public static <T extends AccessibleObject> T accessible(T accessible) {
        if (accessible == null) return null;
        if (accessible instanceof Member) {
            Member member = (Member) accessible;
            if (Modifier.isPublic(member.getModifiers()) &&
                    Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
                return accessible;
            }
        }
        if (!accessible.isAccessible()) accessible.setAccessible(true);
        return accessible;
    }


    private static class NULL {
    }

    public static class ReflectException extends RuntimeException {

        public ReflectException(String message) {
            super(message);
        }

        public ReflectException(String message, Throwable cause) {
            super(message, cause);
        }

        public ReflectException(Throwable cause) {
            super(cause);
        }
    }
}
