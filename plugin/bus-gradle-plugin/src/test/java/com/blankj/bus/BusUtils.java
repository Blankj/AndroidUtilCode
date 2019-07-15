package com.blankj.bus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/10/02
 *     desc  : utils about bus
 * </pre>
 */
public final class BusUtils {

    private static final Object NULL = "nULl";
    private static final String TAG  = "BusUtils";

    private final Map<String, Set<Object>>         mClassName_BusesMap          = new ConcurrentHashMap<>();
    private final Map<String, BusInfo>             mTag_BusInfoMap              = new HashMap<>();
    private final Map<String, Map<String, Object>> mClassName_Tag_Arg4StickyMap = new ConcurrentHashMap<>();

    private BusUtils() {
        init();
    }

    /**
     * It'll be injected the bus who have {@link Bus} annotation
     * by function of {@link BusUtils#registerBus} when execute transform task.
     */
    private void init() {/*inject*/}

    private void registerBus(String tag,
                             String className, String funName, String paramType, String paramName,
                             boolean sticky, String threadMode) {
        mTag_BusInfoMap.put(tag, new BusInfo(className, funName, paramType, paramName, sticky, threadMode));
    }

    public static void register(final Object bus) {
        getInstance().registerInner(bus);
    }

    public static void unregister(final Object bus) {
        getInstance().unregisterInner(bus);
    }

    public static void post(final String tag) {
        post(tag, NULL);
    }

    public static void post(final String tag, final Object arg) {
        getInstance().postInner(tag, arg);
    }

    public static void postSticky(final String tag) {
        postSticky(tag, NULL);
    }

    public static void postSticky(final String tag, final Object arg) {
        getInstance().postStickyInner(tag, arg);
    }

    public static void removeSticky(final String tag) {
        getInstance().removeStickyInner(tag);
    }

    public static String toString_() {
        return getInstance().toString();
    }

    @Override
    public String toString() {
        return "BusUtils: " + mTag_BusInfoMap;
    }

    private static BusUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    private void registerInner(final Object bus) {
        if (bus == null) return;
        String className = bus.getClass().getName();
        synchronized (mClassName_BusesMap) {
            Set<Object> buses = mClassName_BusesMap.get(className);
            if (buses == null) {
                buses = new CopyOnWriteArraySet<>();
                mClassName_BusesMap.put(className, buses);
            }
            buses.add(bus);
        }
        processSticky(bus);
    }

    private void processSticky(final Object bus) {
        Map<String, Object> tagArgMap = mClassName_Tag_Arg4StickyMap.get(bus.getClass().getName());
        if (tagArgMap == null) return;
        synchronized (mClassName_Tag_Arg4StickyMap) {
            for (Map.Entry<String, Object> tagArgEntry : tagArgMap.entrySet()) {
                postInner(tagArgEntry.getKey(), tagArgEntry.getValue());
            }
        }
    }

    private void unregisterInner(final Object bus) {
        if (bus == null) return;
        String className = bus.getClass().getName();
        synchronized (mClassName_BusesMap) {
            Set<Object> buses = mClassName_BusesMap.get(className);
            if (buses == null || !buses.contains(bus)) {
                System.out.println("The bus of <" + bus + "> was not registered before.");
                return;
            }
            buses.remove(bus);
        }
    }

    private void postInner(final String tag, final Object arg) {
        postInner(tag, arg, false);
    }

    private void postInner(final String tag, final Object arg, final boolean sticky) {
        BusInfo busInfo = mTag_BusInfoMap.get(tag);
        if (busInfo == null) {
            System.out.println("The bus of tag <" + tag + "> is not exists.");
            return;
        }
        if (busInfo.method == null) {
            Method method = getMethodByBusInfo(busInfo);
            if (method == null) {
                return;
            }
            busInfo.method = method;
        }
        invokeMethod(tag, arg, busInfo, sticky);
    }

    private Method getMethodByBusInfo(BusInfo busInfo) {
        try {
            if ("".equals(busInfo.paramType)) {
                return Class.forName(busInfo.className).getDeclaredMethod(busInfo.funName);
            } else {
                return Class.forName(busInfo.className).getDeclaredMethod(busInfo.funName, Class.forName(busInfo.paramType));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void invokeMethod(final String tag, final Object arg, final BusInfo busInfo, final boolean sticky) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                realInvokeMethod(tag, arg, busInfo, sticky);
            }
        };
        switch (busInfo.threadMode) {
//            case "MAIN":
//                Utils.runOnUiThread(runnable);
//                return;
//            case "IO":
//                ThreadUtils.getIoPool().execute(runnable);
//                return;
//            case "CPU":
//                ThreadUtils.getCpuPool().execute(runnable);
//                return;
//            case "CACHED":
//                ThreadUtils.getCachedPool().execute(runnable);
//                return;
//            case "SINGLE":
//                ThreadUtils.getSinglePool().execute(runnable);
//                return;
            default:
                runnable.run();
        }
    }

    private void realInvokeMethod(final String tag, Object arg, BusInfo busInfo, boolean sticky) {
        Set<Object> buses = mClassName_BusesMap.get(busInfo.className);
        if (buses == null || buses.size() == 0) {
            if (!sticky) {
                System.out.println("The bus of tag <" + tag + "> was not registered before.");
                return;
            } else {
                return;
            }
        }
        try {
            if (arg == NULL) {
                for (Object bus : buses) {
                    busInfo.method.invoke(bus);
                }
            } else {
                for (Object bus : buses) {
                    busInfo.method.invoke(bus, arg);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void postStickyInner(final String tag, final Object arg) {
        BusInfo busInfo = mTag_BusInfoMap.get(tag);
        if (busInfo == null) {
            System.out.println("The bus of tag <" + tag + "> is not exists.");
            return;
        }
        if (!busInfo.sticky) { // not sticky bus will post directly.
            postInner(tag, arg);
            return;
        }
        synchronized (mClassName_Tag_Arg4StickyMap) {
            Map<String, Object> tagArgMap = mClassName_Tag_Arg4StickyMap.get(busInfo.className);
            if (tagArgMap == null) {
                tagArgMap = new HashMap<>();
                mClassName_Tag_Arg4StickyMap.put(busInfo.className, tagArgMap);
            }
            tagArgMap.put(tag, arg);
        }
        postInner(tag, arg, true);
    }

    private void removeStickyInner(final String tag) {
        BusInfo busInfo = mTag_BusInfoMap.get(tag);
        if (busInfo == null) {
            System.out.println("The bus of tag <" + tag + "> is not exists.");
            return;
        }
        if (!busInfo.sticky) {
            System.out.println("The bus of tag <" + tag + "> is not sticky.");
            return;
        }
        synchronized (mClassName_Tag_Arg4StickyMap) {
            Map<String, Object> tagArgMap = mClassName_Tag_Arg4StickyMap.get(busInfo.className);
            if (tagArgMap == null || !tagArgMap.containsKey(tag)) {
                System.out.println("The sticky bus of tag <" + tag + "> didn't post.");
                return;
            }
            tagArgMap.remove(tag);
        }
    }

    private static final class BusInfo {

        String  className;
        String  funName;
        String  paramType;
        String  paramName;
        boolean sticky;
        String  threadMode;
        Method  method;

        BusInfo(String className, String funName, String paramType, String paramName,
                boolean sticky, String threadMode) {
            this.className = className;
            this.funName = funName;
            this.paramType = paramType;
            this.paramName = paramName;
            this.sticky = sticky;
            this.threadMode = threadMode;
        }

        @Override
        public String toString() {
            return "BusInfo { desc: " + className + "#" + funName +
                    ("".equals(paramType) ? "()" : ("(" + paramType + " " + paramName + ")")) +
                    ", sticky: " + sticky +
                    ", threadMode: " + threadMode +
                    ", method: " + method +
                    " }";
        }
    }

    public enum ThreadMode {
        MAIN, IO, CPU, CACHED, SINGLE, POSTING
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.CLASS)
    public @interface Bus {
        String tag();

        boolean sticky() default false;

        ThreadMode threadMode() default ThreadMode.POSTING;
    }

    private static class LazyHolder {
        private static final BusUtils INSTANCE = new BusUtils();
    }
}