package com.blankj.utilcode.util;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
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

    private static final Object NULL   = "nULl";
    private static final String TAG    = "BusUtils";
    private static final String PREFIX = "blankj.bus/";

    private final Map<String, List<BusInfo>> mTag_BusInfoListMap = new HashMap<>();

    private final Map<String, Set<Object>>         mClassName_BusesMap          = new ConcurrentHashMap<>();
    private final Map<String, List<String>>        mClassName_TagsMap           = new HashMap<>();
    private final Map<String, Map<String, Object>> mClassName_Tag_Arg4StickyMap = new ConcurrentHashMap<>();

    private BusUtils() {
        try {
            String[] tags = Utils.getApp().getAssets().list(PREFIX);
            if (tags == null || tags.length == 0) {
                Log.w(TAG, "no bus");
                return;
            }
            for (String tag : tags) {
                String[] busInfos = Utils.getApp().getAssets().list(PREFIX + tag);
                if (busInfos == null || busInfos.length == 0) {
                    Log.w(TAG, "The tag of <" + tag + "> no bus.");
                    continue;
                }
                for (String busInfo : busInfos) {
                    parseBusInfo(tag, busInfo);
                }
                sortBusByPriority(tag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseBusInfo(String tag, String busInfo) {
        String[] split = busInfo.split("-");
        if (split.length != 7) {
            Log.e(TAG, "The tag of <" + tag + ">'s bus <" + busInfo + "> which format is wrong.");
            return;
        }
        String className = split[0];
        String funName = split[1];
        String paramType = split[2];
        String paramName = split[3];
        boolean sticky = Boolean.parseBoolean(split[4]);
        String threadMode = split[5];
        int priority;
        try {
            priority = Integer.parseInt(split[6]);
        } catch (NumberFormatException e) {
            Log.e(TAG, "The tag of <" + tag + ">'s bus <" + busInfo + "> which format is wrong.");
            return;
        }
        registerBusInner(tag, className, funName, paramType, paramName, sticky, threadMode, priority);
    }

    private void sortBusByPriority(String tag) {
        List<BusInfo> busInfoList = mTag_BusInfoListMap.get(tag);
        if (busInfoList != null && busInfoList.size() > 1) {
            Collections.sort(busInfoList, new Comparator<BusInfo>() {
                @Override
                public int compare(BusInfo o0, BusInfo o1) {
                    return o1.priority - o0.priority;
                }
            });
        }
    }

    private void registerBusInner(String tag,
                                  String className, String funName, String paramType, String paramName,
                                  boolean sticky, String threadMode, int priority) {
        List<BusInfo> busInfoList = mTag_BusInfoListMap.get(tag);
        if (busInfoList == null) {
            busInfoList = new ArrayList<>();
            mTag_BusInfoListMap.put(tag, busInfoList);
        }
        busInfoList.add(new BusInfo(className, funName, paramType, paramName, sticky, threadMode, priority));
    }

    public static void registerBus(String tag,
                                   String className, String funName, String paramType, String paramName,
                                   boolean sticky, String threadMode, int priority) {
        getInstance().registerBusInner(tag, className, funName, paramType, paramName, sticky, threadMode, priority);
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
        return "BusUtils: " + mTag_BusInfoListMap;
    }

    static Runnable getPreLoadRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                preLoad();
            }
        };
    }

    private static void preLoad() {
        //noinspection ResultOfMethodCallIgnored
        getInstance();
    }

    private static BusUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    private void registerInner(final Object bus) {
        if (bus == null) return;
        Class aClass = bus.getClass();
        String className = aClass.getName();
        synchronized (mClassName_BusesMap) {
            Set<Object> buses = mClassName_BusesMap.get(className);
            if (buses == null) {
                buses = new CopyOnWriteArraySet<>();
                mClassName_BusesMap.put(className, buses);
            }
            buses.add(bus);
        }
        List<String> tags = mClassName_TagsMap.get(className);
        if (tags == null) {
            synchronized (mClassName_TagsMap) {
                tags = mClassName_TagsMap.get(className);
                if (tags == null) {
                    tags = new ArrayList<>();
                    for (Map.Entry<String, List<BusInfo>> entry : mTag_BusInfoListMap.entrySet()) {
                        for (BusInfo busInfo : entry.getValue()) {
                            try {
                                if (Class.forName(busInfo.className).isAssignableFrom(aClass)) {
                                    tags.add(entry.getKey());
                                    busInfo.subClassNames.add(className);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    mClassName_TagsMap.put(className, tags);
                }
            }
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
                Log.e(TAG, "The bus of <" + bus + "> was not registered before.");
                return;
            }
            buses.remove(bus);
        }
    }

    private void postInner(final String tag, final Object arg) {
        postInner(tag, arg, false);
    }

    private void postInner(final String tag, final Object arg, final boolean sticky) {
        List<BusInfo> busInfoList = mTag_BusInfoListMap.get(tag);
        if (busInfoList == null) {
            Log.e(TAG, "The bus of tag <" + tag + "> is not exists.");
            return;
        }
        for (BusInfo busInfo : busInfoList) {
            if (busInfo.method == null) {
                Method method = getMethodByBusInfo(busInfo);
                if (method == null) {
                    Log.e(TAG, "The bus of tag <" + tag + ">'s method <" + busInfo.funName +
                            ("".equals(busInfo.paramType) ? "()" : ("(" + busInfo.paramType + " " + busInfo.paramName + ")"))
                            + "> is not exists.");
                    return;
                }
                busInfo.method = method;
            }
            invokeMethod(tag, arg, busInfo, sticky);
        }
    }

    private Method getMethodByBusInfo(BusInfo busInfo) {
        try {
            if ("".equals(busInfo.paramType)) {
                return Class.forName(busInfo.className).getDeclaredMethod(busInfo.funName);
            } else {
                return Class.forName(busInfo.className).getDeclaredMethod(busInfo.funName, getClassName(busInfo.paramType));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class getClassName(String paramType) throws ClassNotFoundException {
        switch (paramType) {
            case "boolean":
                return boolean.class;
            case "int":
                return int.class;
            case "long":
                return long.class;
            case "short":
                return short.class;
            case "byte":
                return byte.class;
            case "double":
                return double.class;
            case "float":
                return float.class;
            case "char":
                return char.class;
            default:
                return Class.forName(paramType);
        }
    }

    private void invokeMethod(final String tag, final Object arg, final BusInfo busInfo, final boolean sticky) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                realInvokeMethod(tag, arg, busInfo, sticky);
            }
        };
        switch (busInfo.threadMode) {
            case "MAIN":
                UtilsBridge.runOnUiThread(runnable);
                return;
            case "IO":
                ThreadUtils.getIoPool().execute(runnable);
                return;
            case "CPU":
                ThreadUtils.getCpuPool().execute(runnable);
                return;
            case "CACHED":
                ThreadUtils.getCachedPool().execute(runnable);
                return;
            case "SINGLE":
                ThreadUtils.getSinglePool().execute(runnable);
                return;
            default:
                runnable.run();
        }
    }

    private void realInvokeMethod(final String tag, Object arg, BusInfo busInfo, boolean sticky) {
        Set<Object> buses = new HashSet<>();
        for (String subClassName : busInfo.subClassNames) {
            Set<Object> subBuses = mClassName_BusesMap.get(subClassName);
            if (subBuses != null && !subBuses.isEmpty()) {
                buses.addAll(subBuses);
            }
        }
        if (buses.size() == 0) {
            if (!sticky) {
                Log.e(TAG, "The bus of tag <" + tag + "> was not registered before.");
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
        List<BusInfo> busInfoList = mTag_BusInfoListMap.get(tag);
        if (busInfoList == null) {
            Log.e(TAG, "The bus of tag <" + tag + "> is not exists.");
            return;
        }
        for (BusInfo busInfo : busInfoList) {
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
    }

    private void removeStickyInner(final String tag) {
        List<BusInfo> busInfoList = mTag_BusInfoListMap.get(tag);
        if (busInfoList == null) {
            Log.e(TAG, "The bus of tag <" + tag + "> is not exists.");
            return;
        }
        for (BusInfo busInfo : busInfoList) {
            if (!busInfo.sticky) {
                Log.e(TAG, "The bus of tag <" + tag + "> is not sticky.");
                return;
            }
            synchronized (mClassName_Tag_Arg4StickyMap) {
                Map<String, Object> tagArgMap = mClassName_Tag_Arg4StickyMap.get(busInfo.className);
                if (tagArgMap == null || !tagArgMap.containsKey(tag)) {
                    Log.e(TAG, "The sticky bus of tag <" + tag + "> didn't post.");
                    return;
                }
                tagArgMap.remove(tag);
            }
        }
    }

    private static final class BusInfo {

        String       className;
        String       funName;
        String       paramType;
        String       paramName;
        boolean      sticky;
        String       threadMode;
        int          priority;
        Method       method;
        List<String> subClassNames;

        BusInfo(String className, String funName, String paramType, String paramName,
                boolean sticky, String threadMode, int priority) {
            this.className = className;
            this.funName = funName;
            this.paramType = paramType;
            this.paramName = paramName;
            this.sticky = sticky;
            this.threadMode = threadMode;
            this.priority = priority;
            this.subClassNames = new CopyOnWriteArrayList<>();
        }

        @Override
        public String toString() {
            return "BusInfo { desc: " + className + "#" + funName +
                    ("".equals(paramType) ? "()" : ("(" + paramType + " " + paramName + ")")) +
                    ", sticky: " + sticky +
                    ", threadMode: " + threadMode +
                    ", method: " + method +
                    ", priority: " + priority +
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

        int priority() default 0;
    }

    private static class LazyHolder {
        private static final BusUtils INSTANCE = new BusUtils();
    }
}