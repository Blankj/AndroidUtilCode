package com.blankj.utilcode.util.reflect;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/12
 *     desc  :
 * </pre>
 */
public class TestHierarchicalMethodsBase {

    public static String PUBLIC_RESULT  = "PUBLIC_BASE";
    public static String PRIVATE_RESULT = "PRIVATE_BASE";

    private int invisibleField1;
    private int invisibleField2;
    public  int visibleField1;
    public  int visibleField2;

    public String pub_base_method(int number) {
        return PUBLIC_RESULT;
    }

    public String pub_method(int number) {
        return PUBLIC_RESULT;
    }

    private String priv_method(int number) {
        return PRIVATE_RESULT;
    }

    private String very_priv_method() {
        return PRIVATE_RESULT;
    }
}
