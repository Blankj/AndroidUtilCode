package com.blankj.api;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/09
 *     desc  :
 * </pre>
 */
public class ApiTest {

    @Test
    public void testInject() throws IOException {
        inject2ApiUtils(getApiImplMap());
    }

    private static Map<String, ApiInfo> getApiImplMap() throws IOException {
        Map<String, ApiInfo> apiImplMap = new HashMap<>();
        List<String> apiClasses = new ArrayList<>();

        ClassReader cr = new ClassReader(TestApiImpl.class.getName());
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new ApiClassVisitor(cw, apiImplMap, apiClasses, ApiUtils.class.getCanonicalName());
        cr.accept(cv, ClassReader.SKIP_FRAMES);

        System.out.println("apiImplMap = " + apiImplMap);

        apiClasses = new ArrayList<>();
        cr = new ClassReader(TestApi.class.getName());
        cw = new ClassWriter(cr, 0);
        cv = new ApiClassVisitor(cw, apiImplMap, apiClasses, ApiUtils.class.getCanonicalName());
        cr.accept(cv, ClassReader.SKIP_FRAMES);

        System.out.println("apiClasses = " + apiClasses);
        return apiImplMap;
    }

    private static void inject2ApiUtils(Map<String, ApiInfo> apiImpls) throws IOException {
        ClassReader cr = new ClassReader(ApiUtils.class.getName());
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new ApiUtilsClassVisitor(cw, apiImpls, ApiUtils.class.getCanonicalName());
        cr.accept(cv, ClassReader.SKIP_FRAMES);

        FileUtils.writeByteArrayToFile(new File("ApiUtils2333.class"), cw.toByteArray());
    }

    @ApiUtils.Api(isMock = true)
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