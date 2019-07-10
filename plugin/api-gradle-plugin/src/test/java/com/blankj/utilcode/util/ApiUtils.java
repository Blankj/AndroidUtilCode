package com.blankj.utilcode.util;

import com.android.annotations.NonNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/09
 *     desc  :
 * </pre>
 */
public class ApiUtils {

    private static final String TAG = "ApiUtils";

    private Map<Class, BaseApi> mApiMap           = new HashMap<>();
    private Map<Class, Class>   mInjectApiImplMap = new HashMap<>();

    private ApiUtils() {
        init();
    }

    /**
     * Get api.
     *
     * @param apiClass The class of api.
     * @param <T>      The type.
     * @return the api
     */
    public static <T extends BaseApi> T getApi(@NonNull Class<T> apiClass) {
        return getInstance().getApiInner(apiClass);
    }

    public static String toString_() {
        return getInstance().toString();
    }

    @Override
    public String toString() {
        return "apis: " + mApiMap +
                "\ninjectApis: " + mInjectApiImplMap;
    }

    private static ApiUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * It'll be injected the implClasses who have {@link ApiUtils.Api} annotation
     * by function of {@link ApiUtils#registerImpl} when execute transform task.
     */
    private void init() {/*inject*/}

    private void registerImpl(Class implClass) {
        mInjectApiImplMap.put(implClass.getSuperclass(), implClass);
    }

    private <Result> Result getApiInner(final @NonNull Class apiClass) {
        BaseApi api = mApiMap.get(apiClass);
        if (api == null) {
            Class implClass = mInjectApiImplMap.get(apiClass);
            if (implClass != null) {
                try {
                    api = (BaseApi) implClass.newInstance();
                    mApiMap.put(apiClass, api);
                } catch (Exception ignore) {/**/}
            } else {

            }
            if (api == null) {
                System.out.println(apiClass + " is not register.");
                return null;
            }
        }
        //noinspection unchecked
        return (Result) api.invoke();
    }

    private static class LazyHolder {
        private static final ApiUtils INSTANCE = new ApiUtils();
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.CLASS)
    public @interface Api {
        boolean isDebug() default false;
    }

    public abstract static class BaseApi {
        private BaseApi invoke() {
            return this;
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // test
    ///////////////////////////////////////////////////////////////////////////

    public static abstract class TestApi extends BaseApi {

        public abstract String test(String name);

    }


    @Api
    public static class TestApiImpl extends TestApi {

        @Override
        public String test(String name) {
            System.out.println(name);
            return "haha";
        }

    }
}
