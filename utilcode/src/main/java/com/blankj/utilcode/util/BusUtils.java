package com.blankj.utilcode.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/09/22
 *     desc  : Utils for bus
 * </pre>
 */
public class BusUtils {

    private static final ConcurrentHashMap<String, IBus> BUS = new ConcurrentHashMap<>();

    public static void register(final IBus bus) {
        if (bus == null) return;
        String name = bus.getName();
        if (name == null || name.length() == 0) return;
        if (BUS.containsKey(name)) {
            System.out.println("bus of <" + name + "> has already registered.");
        }
        BUS.put(name, bus);
        System.out.println("bus of <" + name + "> register successfully.");
    }

    public static Set<String> getBus() {
        return BUS.keySet();
    }

    public static void call(final String name) {
        if (name == null || name.length() == 0) return;
        IBus bus = BUS.get(name);
        if (bus == null) return;
        bus.onEvent();
    }

    public interface IBus {
        String getName();

        void onEvent();
    }
}
