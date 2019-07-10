package com.blankj.bus


import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/09
 *     desc  :
 * </pre>
 */
public class BusClassVisitor extends ClassVisitor {

    private Map<String, String> mBusMap;
    private String className;

    public BusClassVisitor(ClassVisitor classVisitor, Map<String, String> busMap) {
        super(Opcodes.ASM5, classVisitor);
        mBusMap = busMap;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        className = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (cv == null) return null;
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, descriptor) {
            @Override
            public AnnotationVisitor visitAnnotation(String desc1, boolean visible) {
                if ('Lcom/blankj/utilcode/util/BusUtils$Bus;'.equals(desc)) {
                    return new AnnotationVisitor(Opcodes.ASM5, super.visitAnnotation(desc, visible)) {
                        @Override
                        public void visit(String name1, Object value) {// 可获取注解的值
                            mBusMap.put(value, className)
                        }
                    };
                }
                return super.visitAnnotation(desc, visible);
            }
        };
        return mv
    }
}
