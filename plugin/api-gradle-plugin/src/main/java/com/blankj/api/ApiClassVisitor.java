package com.blankj.api;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

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
public class ApiClassVisitor extends ClassVisitor {

    private Map<String, ApiInfo> mApiImplMap;
    private List<String>         mApiClasses;
    private String               className;
    private String               superClassName;
    private boolean              hasAnnotation;
    private boolean              isMock;
    private String               mApiUtilsClass;
    public  String               errorStr;

    public ApiClassVisitor(ClassVisitor classVisitor, Map<String, ApiInfo> apiImplMap, List<String> apiClasses, String apiUtilsClass) {
        super(Opcodes.ASM5, classVisitor);
        mApiImplMap = apiImplMap;
        mApiClasses = apiClasses;
        mApiUtilsClass = apiUtilsClass.replace(".", "/");
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        className = name;
        superClassName = superName;
        if ((mApiUtilsClass + "$BaseApi").equals(superName)) {
            mApiClasses.add(name);
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (("L" + mApiUtilsClass + "$Api;").equals(desc)) {
            hasAnnotation = true;
            return new AnnotationVisitor(Opcodes.ASM5, super.visitAnnotation(desc, visible)) {
                @Override
                public void visit(String name, Object value) {// 可获取注解的值
                    isMock = (boolean) value;
                    super.visit(name, value);
                }
            };
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        if (hasAnnotation) {
            if (!isMock) {// 如果不是 mock 的话
                ApiInfo apiInfo = mApiImplMap.get(superClassName);
                if (apiInfo == null) {
                    mApiImplMap.put(superClassName, new ApiInfo(className, false));
                } else {// 存在一个 api 多个实现就报错
                    errorStr = "<" + className + "> and <" + apiInfo.implApiClass + "> impl same api of <" + superClassName + ">";
                }
            } else {// mock 的话，如果 map 中已存在就不覆盖了
                if (!mApiImplMap.containsKey(superClassName)) {
                    mApiImplMap.put(superClassName, new ApiInfo(className, true));
                }
            }
        }
    }
}
