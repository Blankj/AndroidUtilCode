package com.blankj.utilcode.util;

import android.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/10/02
 *     desc  : utils about bus
 * </pre>
 */
public final class BusUtils {

    private static final String TAG = "BusUtils";

    private final Map<Class, Set<Object>> mSubscribersMap = new HashMap<>();
    private final Map<String, Class>      mBusesMap       = new HashMap<>();
    private final Set<Sticky>             mStickies       = new HashSet<>();

    private BusUtils() {
        init();
    }

    /**
     * It'll be injected the bus who have {@link BusUtils.Bus} annotation
     * by function of {@link BusUtils#registerBus} when execute transform task.
     */
    private void init() {/*inject*/}

    private void registerBus(final String name, final Class type) {
        mBusesMap.put(name, type);
    }

    public static void register(final Object bus) {
        getInstance().registerInner(bus);
    }

    public static void unregister(final Object bus) {
        getInstance().unregisterInner(bus);
    }

    private void registerInner(final Object bus) {
        if (bus == null) return;
        Class aClass = bus.getClass();
        Set<Object> typeSubscribers = mSubscribersMap.get(aClass);
        if (typeSubscribers == null) {
            typeSubscribers = new HashSet<>();
            mSubscribersMap.put(aClass, typeSubscribers);
        }
        typeSubscribers.add(bus);
    }

    private void unregisterInner(final Object bus) {
        if (bus == null) return;
        Class aClass = bus.getClass();
        Set<Object> typeSubscribers = mSubscribersMap.get(aClass);
        if (typeSubscribers == null || typeSubscribers.contains(bus)) {
            Log.i(TAG, "Bus to unregister was not registered before: " + bus);
            return;
        }
        typeSubscribers.remove(bus);
    }

    private void postStickyInner(final String name, final Object... objects) {
        mStickies.add(new Sticky(name, objects));
        postInner(name, objects);
    }

    private void removeStickyInner(final String name) {

    }

    private void postInner(final String name, final Object... objects) {
        if (name == null || name.length() == 0) return;
        final Class buses = mBusesMap.get(name);


//        for (Class bus : buses) {
//            Set<Object> subscribers = mSubscribersMap.get(bus);
//            if (subscribers == null || subscribers.isEmpty()) {
//                Log.e("BusUtils", "bus of <" + name + "{" + bus + "}> in didn\'t exist.");
//                continue;
//            }
//            for (Object subscriber : subscribers) {
//                injectShell2(subscriber, name, objects);
//            }
//        }
    }

    private static BusUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class Sticky {
        String   name;
        Object[] params;

        Sticky(String name, Object[] params) {
            this.name = name;
            this.params = params;
        }
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface Bus {
        String value() default "";
    }

    private static class LazyHolder {
        private static final BusUtils INSTANCE = new BusUtils();
    }
}
