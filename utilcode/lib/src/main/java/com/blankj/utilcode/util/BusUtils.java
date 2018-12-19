package com.blankj.utilcode.util;


import android.util.Log;

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
 *     https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/README-STATIC-BUS.md
 *     will help u.
 * </pre>
 */
public final class BusUtils {

    private static final Object NULL = new Object();

    public static <T> T post(final String name, final Object... objects) {
        if (name == null || name.length() == 0) return null;
        Object o = injectShell(name, objects);
        if (NULL.equals(o)) {
            Log.e("BusUtils", "bus of <" + name + "> didn\'t exist.");
            return null;
        }
        return (T) o;
    }

    private static Object injectShell(final String name, final Object[] objects) {
        return NULL;
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface Subscribe {
        String name() default "";

        int priority() default 0;
    }

//    private static final Map<String, TreeSet<Bus>> BUSES = new ConcurrentHashMap<>();

//    public static void register(final Object bus) {
//        Class c = null;
//        if (bus.getClass().equals(c)) {
//
//        }
//    }

//    public static void unregister(final Object bus) {
//        if (bus == null) return;
//        Class busClass = bus.getClass();
//        if (BUSES.containsKey(busClass)) {
//
//        }
//    }

//    private static Object inject(final String name, int priority ){
//        if (!BUSES.containsKey(name)) return NULL;
//        TreeSet<Bus> buses = BUSES.get(name);
//        for (Bus bus : buses) {
//            bus.
//        }
//        return NULL;
//    }

//    static class Bus implements Comparable<Bus> {
//
//        TreeMap<Object, Integer> mTreeMap;
//
//        Bus(Object object) {
//            this(object, 0);
//        }
//
//        Bus(Object object, int priority) {
//            mTreeMap = new TreeMap<>();
//            this.priority = priority;
//        }
//
//        @Override
//        public int compareTo(@NonNull Bus o) {
//            return (this.priority < o.priority) ? 1 : ((this.priority == o.priority) ? 0 : -1);
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            return ObjectUtils.equals(this, obj);
//        }
//
//        @Override
//        public String toString() {
//            return mObject.toString() + ": " + priority;
//        }
//    }
//
//    public static void main(String[] args) {
//        TreeSet<Bus> queue = new TreeSet<>();
//        Bus bus0 = new Bus("", 1);
//        Bus bus1 = new Bus("", 2);
//        Bus bus2 = new Bus("", 3);
//        Bus bus3 = new Bus("", 3);
//        queue.add(bus1);
//        queue.add(bus2);
//        queue.add(bus0);
//
//        System.out.println(queue);
//
//    }
}
