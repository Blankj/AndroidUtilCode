package com.blankj.utilcode.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/09
 *     desc  : utils about api
 * </pre>
 */
public final class ApiUtils {

    private static final String TAG = "ApiUtils";

    private static final String PREFIX = "blankj.api/";

    private Map<Class, Object> apiClass_apiInstance_map  = new ConcurrentHashMap<>();
    private Map<Class, Class>  apiClass_apiImplClass_map = new HashMap<>();

    private ApiUtils() {
    }

    /**
     * Get api.
     *
     * @param apiClass The class of api.
     * @param <T>      The type.
     * @return the api
     */
    public static <T extends BaseApi> T getApi(@NonNull final Class<T> apiClass) {
        return getInstance().getApiInner(apiClass);
    }

    public static String toString_() {
        return getInstance().toString();
    }

    @Override
    public String toString() {
        getAllApis();
        StringBuilder sb = new StringBuilder();
        sb.append("ApiUtils {");
        for (Map.Entry<Class, Class> entry : apiClass_apiImplClass_map.entrySet()) {
            sb.append("\n    ")
                    .append(entry.getKey().getName())
                    .append(": ")
                    .append(entry.getValue().getName());
        }
        sb.append("\n}");
        return sb.toString();
    }

    private static ApiUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    private <Result> Result getApiInner(Class apiClass) {
        Object apiInstance = apiClass_apiInstance_map.get(apiClass);
        if (apiInstance == null) {
            synchronized (this) {
                apiInstance = apiClass_apiInstance_map.get(apiClass);
                if (apiInstance == null) {
                    Class implClass = getApiImplClass(apiClass);
                    if (implClass != null) {
                        try {
                            apiInstance = implClass.newInstance();
                            apiClass_apiInstance_map.put(apiClass, apiInstance);
                        } catch (Exception ignore) {
                            Log.e(TAG, "The api of <" + implClass + "> has no parameterless constructor.");
                            return null;
                        }
                    } else {
                        Log.e(TAG, "The api of <" + apiClass + "> doesn't implement.");
                        return null;
                    }
                }
            }
        }
        //noinspection unchecked
        return (Result) apiInstance;
    }

    private Class getApiImplClass(Class apiClass) {
        Class apiImplClass = apiClass_apiImplClass_map.get(apiClass);
        if (apiImplClass != null) return apiImplClass;
        try {
            String[] apiImpls = Utils.getApp().getAssets().list(PREFIX + apiClass.getName());
            if (apiImpls == null) {
                return null;
            }
            if (apiImpls.length != 1) {
                Log.e(TAG, "The api of <" + apiClass + "> has more than one implement.");
                return null;
            }
            String apiImpl = apiImpls[0];
            if (TextUtils.isEmpty(apiImpl)) {
                Log.e(TAG, "The api of <" + apiClass + ">'s name is empty.");
                return null;
            }
            String[] apiImpl_isMock = apiImpl.split("-");
            if (apiImpl_isMock.length != 2) {
                Log.e(TAG, "The api of <" + apiClass + ">'s implement <" + apiImpl
                        + "> which format of name is wrong.");
                return null;
            }
            String className = apiImpl_isMock[0];
            boolean isMock = Boolean.parseBoolean(apiImpl_isMock[1]);
            if (TextUtils.isEmpty(className)) {
                return null;
            }
            apiImplClass = Class.forName(className);
            return registerApiInner(apiClass, apiImplClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class registerApiInner(Class apiClass, Class apiImplClass) {
        if (apiImplClass == null) return null;
        if (apiClass == null) {
            Class superclass = apiImplClass.getSuperclass();
            if (superclass == null) {
                Log.e(TAG, "<" + apiImplClass.getName() + ">'s superClass is null");
                return null;
            }
            apiClass = superclass;
        }
        //noinspection unchecked
        if (apiClass.isAssignableFrom(apiImplClass)) {
            apiClass_apiImplClass_map.put(apiClass, apiImplClass);
            return apiImplClass;
        } else {
            Log.e(TAG, "<" + apiImplClass.getName() + ">'s superClass is <"
                    + apiClass.getName() + ">, not <" + apiClass.getName() + ">");
            return null;
        }

    }

    static void registerApi(Class<? extends BaseApi> implClass) {
        getInstance().registerApiInner(null, implClass);
    }

    private void getAllApis() {
        try {
            String[] apis = Utils.getApp().getAssets().list(PREFIX);
            if (apis == null) return;
            for (String api : apis) {
                try {
                    getApiImplClass(Class.forName(api));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class LazyHolder {
        private static final ApiUtils INSTANCE = new ApiUtils();
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.CLASS)
    public @interface Api {
        boolean isMock() default false;
    }

    public abstract static class BaseApi {
    }
}