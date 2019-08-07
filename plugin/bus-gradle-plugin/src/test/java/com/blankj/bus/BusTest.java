package com.blankj.bus;


import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
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
public class BusTest {

    private static final String TAG_NO_PARAM         = "TagNoParam";
    private static final String TAG_ONE_PARAM        = "TagOneParam";
    private static final String TAG_NO_PARAM_STICKY  = "TagNoParamSticky";
    private static final String TAG_ONE_PARAM_STICKY = "TagOneParamSticky";

    @BusUtils.Bus(tag = TAG_NO_PARAM)
    public void noParamFun() {
        System.out.println("noParam");
    }

    @BusUtils.Bus(tag = TAG_NO_PARAM, priority = 1)
    public void sameTagP1Fun() {
        System.out.println("noParam");
    }

    @BusUtils.Bus(tag = TAG_NO_PARAM)
    public void sameTagParam2Fun(int arg0, Object arg1) {
        System.out.println("params2");
    }

    @BusUtils.Bus(tag = "params2")
    public void param2Fun(int arg0, Object arg1) {
        System.out.println("params2");
    }

    @BusUtils.Bus(tag = TAG_ONE_PARAM)
    public void oneParamFun(String param) {
        System.out.println(param);
    }

    @BusUtils.Bus(tag = TAG_NO_PARAM_STICKY, sticky = true)
    public void noParamStickyFun() {
        System.out.println("noParamSticky");
    }

    @BusUtils.Bus(tag = TAG_ONE_PARAM_STICKY, sticky = true)
    public void oneParamStickyFun(Callback callback) {
        if (callback != null) {
            System.out.println(callback.call());
        }
    }

    @BusUtils.Bus(tag = "manyparam", threadMode = BusUtils.ThreadMode.SINGLE)
    public void haha(int a, int b) {
        final Thread thread = Thread.currentThread();
        System.out.println(new Callback() {
            @Override
            public String call() {
                return thread.toString();
            }
        });
    }

    @Test
    public void testInject() throws IOException {
        inject2BusUtils(getBuses());
    }

    private static Map<String, List<BusInfo>> getBuses() throws IOException {
        Map<String, List<BusInfo>> busMap = new HashMap<>();

        ClassReader cr = new ClassReader(BusTest.class.getName());
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new BusClassVisitor(cw, busMap, BusUtils.class.getName());
        cr.accept(cv, ClassReader.SKIP_FRAMES);

        for (List<BusInfo> value : busMap.values()) {
            value.sort(new Comparator<BusInfo>() {
                @Override
                public int compare(BusInfo t0, BusInfo t1) {
                    return t1.priority - t0.priority;
                }
            });
        }
        System.out.println("busMap = " + busMap);
        return busMap;
    }

    private static void inject2BusUtils(Map<String, List<BusInfo>> busMap) throws IOException {
        ClassReader cr = new ClassReader(BusUtils.class.getName());
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new BusUtilsClassVisitor(cw, busMap, BusUtils.class.getName());
        cr.accept(cv, ClassReader.SKIP_FRAMES);

        FileUtils.writeByteArrayToFile(new File("BusUtils2333.class"), cw.toByteArray());
    }

    public interface Callback {
        String call();
    }
}