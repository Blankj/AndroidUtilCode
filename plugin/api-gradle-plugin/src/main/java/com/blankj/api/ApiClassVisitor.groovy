package com.blankj.api


import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/09
 *     desc  :
 * </pre>
 */
public class ApiClassVisitor extends ClassVisitor {

    private Map<String, String> mApiImplMap;
    private List<String> mApiClasses;
    private String className;
    private String superClassName;
    private boolean hasAnnotation;
    private boolean isDebug;

    public ApiClassVisitor(ClassVisitor classVisitor, Map<String, String> apiImplMap, List<String> apiClasses) {
        super(Opcodes.ASM5, classVisitor);
        mApiImplMap = apiImplMap;
        mApiClasses = apiClasses;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        className = name;
        superClassName = superName;
        if ('com/blankj/utilcode/util/ApiUtils$BaseApi'.equals(superName)) {
            mApiClasses.add(name);
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if ('Lcom/blankj/utilcode/util/ApiUtils$Api;'.equals(desc)) {
            hasAnnotation = true;
            return new AnnotationVisitor(Opcodes.ASM5, super.visitAnnotation(desc, visible)) {
                @Override
                public void visit(String name, Object value) {// 可获取注解的值
                    isDebug = value;
                    super.visit(name, value);
                }
            };
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    void visitEnd() {
        super.visitEnd()
        if (hasAnnotation) {
            if (!isDebug) {// 如果不是 debug 的话，那么写入
                mApiImplMap.put(superClassName, className);
            } else {// debug 的话，如果 map 中已存在就不覆盖了
                if (!mApiImplMap.containsKey(superClassName)) {
                    mApiImplMap.put(superClassName, className);
                }
            }
        }
    }
}
