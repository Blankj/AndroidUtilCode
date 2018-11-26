package com.blankj.bus;


import com.blankj.utilcode.util.BusUtils;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/09/21
 *     desc  :
 * </pre>
 */
public class BusTest {

    @Test
    public void test() throws Exception {
        String rootPath = "/Users/blankj/Repo/AndroidUtilCode/app/build/intermediates/transforms/busTransform/debug/43/";
        File root = new File(rootPath);
        ClassPool mPool  = new ClassPool(null);
        mPool.appendSystemPath();
        mPool.appendClassPath(rootPath);
        if (root.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(root, new String[]{"class"}, true);

            for (File file : files) {
                String fileName = file.getName();

                String filePath = file.getCanonicalPath();
                String packagePath = filePath.replace(rootPath, "");
                String className = packagePath.replace(System.getProperty("file.separator"), ".");
                // delete .class
                className = className.substring(0, className.length() - 6);

                CtClass ctClass = mPool.get(className);

                CtMethod[] methods = ctClass.getDeclaredMethods();
                for (CtMethod method : methods) {
                    if (method.hasAnnotation(BusUtils.Subscribe.class)) {
                        String name = ((BusUtils.Subscribe) method.getAnnotation(BusUtils.Subscribe.class)).name();
                        String sign = method.getReturnType().getName() + ' ' + method.getLongName();
                        System.out.println(name + ": " + sign);
                    }
                }
            }
        }
    }
}
