package com.blankj.bus;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

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
public class BusUtilsClassVisitor extends ClassVisitor {

    private Map<String, List<BusInfo>> mBusMap;
    private String                     mBusUtilsClass;

    public BusUtilsClassVisitor(ClassVisitor classVisitor, Map<String, List<BusInfo>> busMap, String busUtilsClass) {
        super(Opcodes.ASM5, classVisitor);
        mBusMap = busMap;
        mBusUtilsClass = busUtilsClass.replace(".", "/");
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (!"init".equals(name)) {
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
        // 往 init() 函数中写入
        if (cv == null) return null;
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, descriptor) {

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                return super.visitAnnotation(desc, visible);
            }

            @Override
            protected void onMethodEnter() {
                super.onMethodEnter();
            }

            @Override
            protected void onMethodExit(int opcode) {
                super.onMethodExit(opcode);
                for (Map.Entry<String, List<BusInfo>> busEntry : mBusMap.entrySet()) {
                    List<BusInfo> infoList = busEntry.getValue();
                    for (BusInfo busInfo : infoList) {
                        if (!busInfo.isParamSizeNoMoreThanOne) continue;
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitLdcInsn(busEntry.getKey());
                        mv.visitLdcInsn(busInfo.className);
                        mv.visitLdcInsn(busInfo.funName);
                        if (busInfo.paramsInfo.size() == 1) {
                            mv.visitLdcInsn(busInfo.paramsInfo.get(0).className);
                            mv.visitLdcInsn(busInfo.paramsInfo.get(0).name);
                        } else {
                            mv.visitLdcInsn("");
                            mv.visitLdcInsn("");
                        }
                        mv.visitInsn(busInfo.sticky ? ICONST_1 : ICONST_0);
                        mv.visitLdcInsn(busInfo.threadMode);
                        mv.visitIntInsn(SIPUSH, busInfo.priority);
                        mv.visitMethodInsn(INVOKESPECIAL, mBusUtilsClass, "registerBus", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;I)V", false);
                    }
                }
            }
        };
        return mv;
    }
}
