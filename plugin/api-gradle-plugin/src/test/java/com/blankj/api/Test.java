package com.blankj.api;

import com.blankj.utilcode.util.ApiUtils;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/09
 *     desc  :
 * </pre>
 */
public class Test extends ClassLoader {
    public static void main(String[] args) throws Exception {
        injectApiImpls(getApiImpls());
    }

    private static List<String> getApiImpls() throws IOException {
        List<String> apiImplClass = new ArrayList<>();

        ClassReader cr = new ClassReader(ApiUtils.TestApi.class.getName());
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ApiClassVisitor(cw, apiImplClass);

        cr.accept(cv, Opcodes.ASM5);
        return apiImplClass;
    }

    private static void injectApiImpls(List<String> apiImpls) throws IOException {
        ClassReader cr = new ClassReader(ApiUtils.class.getName());
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ApiUtilsClassVisitor(cw, apiImpls);

        cr.accept(cv, Opcodes.ASM5);

        byte[] code = cw.toByteArray();

        FileOutputStream fos = new FileOutputStream("ApiUtils2333.class");
        fos.write(code);
        fos.close();
    }

    static class ApiClassVisitor extends ClassVisitor {

        private List<String> mApiImplClasses;
        private String       className;

        public ApiClassVisitor(ClassVisitor classVisitor, List<String> apiImplClasses) {
            super(Opcodes.ASM5, classVisitor);
            mApiImplClasses = apiImplClasses;
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            className = name;
            super.visit(version, access, name, signature, superName, interfaces);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            if ("Lcom/blankj/utilcode/util/ApiUtils$Api;".equals(desc)) {
                mApiImplClasses.add(className);
                return new AnnotationVisitor(Opcodes.ASM5, super.visitAnnotation(desc, visible)) {
                    @Override
                    public void visit(String name, Object value) {
                        System.out.println(name + " = " + value);
                        super.visit(name, value);
                    }
                };
            }
            return super.visitAnnotation(desc, visible);
        }

    }

    static class ApiUtilsClassVisitor extends ClassVisitor {

        private List<String> mApiImplClasses;

        public ApiUtilsClassVisitor(ClassVisitor classVisitor, List<String> apiImplClasses) {
            super(Opcodes.ASM5, classVisitor);
            mApiImplClasses = apiImplClasses;
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
                    for (String apiImplClass : mApiImplClasses) {
                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                        mv.visitLdcInsn(Type.getType("L" + apiImplClass + ";"));
                        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/blankj/utilcode/util/ApiUtils", "registerImpl", "(Ljava/lang/Class;)V", false);
                    }
                }
            };
            return mv;
        }
    }
}