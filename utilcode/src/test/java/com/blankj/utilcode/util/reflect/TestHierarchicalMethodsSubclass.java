package com.blankj.utilcode.util.reflect;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/12
 *     desc  :
 * </pre>
 */
public class TestHierarchicalMethodsSubclass extends TestHierarchicalMethodsBase {

    public static String PUBLIC_RESULT  = "PUBLIC_SUB";
    public static String PRIVATE_RESULT = "PRIVATE_SUB";

    // Both of these are hiding fields in the super type
    private int invisibleField2;
    public  int visibleField2;

    private int invisibleField3;
    public  int visibleField3;

    private String priv_method(int number) {
        return PRIVATE_RESULT;
    }

    private String pub_method(Integer number) {
        return PRIVATE_RESULT;
    }
}
