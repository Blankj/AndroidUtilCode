package com.blankj.api;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.Map;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/09
 *     desc  :
 * </pre>
 */
public class ApiUtilsClassVisitor extends ClassVisitor {

    private Map<String, ApiInfo> mApiImplMap;
    private String               mApiUtilsClass;

    public ApiUtilsClassVisitor(ClassVisitor classVisitor, Map<String, ApiInfo> apiImplMap, String apiUtilsClass) {
        super(Opcodes.ASM5, classVisitor);
        mApiImplMap = apiImplMap;
        mApiUtilsClass = apiUtilsClass.replace(".", "/");
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
                for (Map.Entry<String, ApiInfo> apiImplEntry : mApiImplMap.entrySet()) {
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitLdcInsn(Type.getType("L" + apiImplEntry.getValue().implApiClass + ";"));
                    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, mApiUtilsClass, "registerImpl", "(Ljava/lang/Class;)V", false);
                }
            }
        };
        return mv;
    }
}
