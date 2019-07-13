package com.blankj.utilcode.util;

import org.junit.Before;
import org.junit.Test;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/11
 *     desc  :
 * </pre>
 */
public class ApiUtilsTest {

    @Before
    public void setUp() throws Exception {
        ReflectUtils.reflect(ApiUtils.class)
                .method("getInstance")
                .method("registerImpl", TestApiImpl.class);
    }

    @Test
    public void getApi() {
        System.out.println("ApiUtils.getApi(TestApi.class).test(\"hehe\") = " + ApiUtils.getApi(TestApi.class).test("hehe"));
    }

    @Test
    public void toString_() {
        System.out.println("ApiUtils.toString_() = " + ApiUtils.toString_());
    }

    @ApiUtils.Api
    public static class TestApiImpl extends TestApi {

        @Override
        public String test(String param) {
            System.out.println("param = " + param);
            return "haha";
        }

    }

    public static abstract class TestApi extends ApiUtils.BaseApi {

        public abstract String test(String name);

    }
}