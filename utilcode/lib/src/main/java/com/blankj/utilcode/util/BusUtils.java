package com.blankj.utilcode.util;


import android.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/10/02
 *     desc  : utils about bus, and the site of
 *     https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/README-STATIC-BUS.md
 *     will help u.
 * </pre>
 */
public final class BusUtils {

    private static final Object              NULL  = new Object();
    private static final Map<Object, String> BUSES = new ConcurrentHashMap<>();

    public static <T> T post(final String name, final Object... objects) {
        if (name == null || name.length() == 0) return null;
        Object o = injectShell(name, objects);
        if (NULL.equals(o)) {
            Log.e("BusUtils", "bus of <" + name + "> didn\'t exist.");
            return null;
        }
        return (T) o;
    }


    public static void register(final Object bus) {

    }

    public static void unregister(final Object bus) {

    }



    private static Object injectShell(final String name, final Object[] objects) {
        return NULL;
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface Subscribe {
        String name() default "";
    }
}
