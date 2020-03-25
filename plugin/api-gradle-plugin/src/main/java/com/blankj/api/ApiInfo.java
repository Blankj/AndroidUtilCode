package com.blankj.api;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/14
 *     desc  :
 * </pre>
 */
public class ApiInfo {

    public String  implApiClass;
    public boolean isMock;

    public ApiInfo(String implApiClass, boolean isMock) {
        this.implApiClass = implApiClass;
        this.isMock = isMock;
    }

    @Override
    public String toString() {
        return "{ implApiClass: " + implApiClass +
                ", isMock: " + isMock +
                " }";
    }
}
