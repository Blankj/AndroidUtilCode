package com.blankj.utilcode.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class BusUtils {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface Subscribe {
        String name() default "";
    }
}