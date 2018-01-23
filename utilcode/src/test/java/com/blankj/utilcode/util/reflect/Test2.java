package com.blankj.utilcode.util.reflect;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/12
 *     desc  :
 * </pre>
 */
public class Test2 {

    public final Object          n;
    public final ConstructorType constructorType;

    public Test2() {
        this.n = null;
        this.constructorType = ConstructorType.NO_ARGS;
    }

    public Test2(Integer n) {
        this.n = n;
        this.constructorType = ConstructorType.INTEGER;
    }

    public Test2(Number n) {
        this.n = n;
        this.constructorType = ConstructorType.NUMBER;
    }

    public Test2(Object n) {
        this.n = n;
        this.constructorType = ConstructorType.OBJECT;
    }

    public static enum ConstructorType {
        NO_ARGS,
        INTEGER,
        NUMBER,
        OBJECT
    }
}
