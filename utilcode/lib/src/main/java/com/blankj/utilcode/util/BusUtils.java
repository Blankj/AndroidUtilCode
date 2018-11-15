package com.blankj.utilcode.util;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/10/02
 *     desc  : utils about bus, and the site of
 *     https://github.com/Blankj/AndroidUtilCode/utilcode/README-STATIC-BUS.md
 *     will help u.
 * </pre>
 */
public final class BusUtils {

    public static <T> T post(String name, Object... objects) {
        if (name == null || name.length() == 0) return null;
        return null;
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface Subscribe {
        String name() default "";
    }
}
