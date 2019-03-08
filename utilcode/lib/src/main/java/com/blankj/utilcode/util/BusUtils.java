package com.blankj.utilcode.util;


import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

    private static final Object                  NULL            = new Object();
    private static final Map<Class, Set<Object>> SUBSCRIBERS_MAP = new HashMap<>();
    private static final Map<String, Set<Bus>>   BUSES_MAP       = new HashMap<>();
    private static final Set<Sticky>             STICKIES        = new HashSet<>();

    public static <T> T postStatic(final String name, final Object... objects) {
        if (name == null || name.length() == 0) return null;
        Object o = injectShell(name, objects);
        if (NULL.equals(o)) {
            Log.e("BusUtils", "static bus of <" + name + "> didn\'t exist.");
            return null;
        }
        return (T) o;
    }

    private static Object injectShell(final String name, final Object[] objects) {
        return NULL;
    }


    public static void register(final Object subscriber) {
        if (subscriber == null) return;
        Class aClass = subscriber.getClass();
        Set<Object> typeSubscribers = SUBSCRIBERS_MAP.get(aClass);
        if (typeSubscribers == null) {
            typeSubscribers = new HashSet<>();
            SUBSCRIBERS_MAP.put(aClass, typeSubscribers);
        }
        typeSubscribers.add(subscriber);
    }

//    public static void postSticky(final String name, final Object... objects) {
//        STICKIES.add(new Sticky(name, objects));
//        post(name, objects);
//    }
//
//    public static void removeSticky(final String name) {
//
//    }

    public static void post(final String name, final Object... objects) {
        if (name == null || name.length() == 0) return;
        final Set<Bus> buses = BUSES_MAP.get(name);
        for (Bus bus : buses) {
            Set<Object> subscribers = SUBSCRIBERS_MAP.get(bus.type);
            if (subscribers == null || subscribers.isEmpty()) {
                Log.e("BusUtils", "bus of <" + name + "{" + bus + "}> in didn\'t exist.");
                continue;
            }
            for (Object subscriber : subscribers) {
                injectShell2(subscriber, name, objects);
            }
        }
    }

    private static void injectShell2(final Object subscriber, final String name, final Object[] objects) {

    }

    public static void unregister(final Object subscriber) {
        if (subscriber == null) return;
        Class aClass = subscriber.getClass();
        Set<Object> typeSubscribers = SUBSCRIBERS_MAP.get(aClass);
        if (typeSubscribers == null || typeSubscribers.contains(subscriber)) {
            Log.i("BusUtils", "Subscriber to unregister was not registered before: " + subscriber);
            return;
        }
        typeSubscribers.remove(subscriber);
    }

    private static void add(final String name, final Class type, final int priority) {
        Set<Bus> buses = BUSES_MAP.get(name);
        if (buses == null) {
            buses = new TreeSet<>();
            BUSES_MAP.put(name, buses);
        }
        buses.add(new Bus(type, priority));
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface Subscribe {
        String name() default "";

        int priority() default 0;
    }

    static class Sticky {
        String   name;
        Object[] params;

        Sticky(String name, Object[] params) {
            this.name = name;
            this.params = params;
        }
    }

    static class Bus implements Comparable<Bus> {

        private Class type;
        private int   priority;

        Bus(Class type, int priority) {
            this.type = type;
            this.priority = priority;
        }

        @Override
        public int compareTo(@NonNull Bus o) {
            if (o.priority != this.priority) return o.priority - this.priority;
            return o.hashCode() - type.hashCode();
        }

        @Override
        public String toString() {
            return type.getName() + ": " + priority;
        }
    }
}
