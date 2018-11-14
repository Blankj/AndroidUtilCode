package com.blankj.bus;


import org.junit.Test;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/09/21
 *     desc  :
 * </pre>
 */
public class BusTest {

    @Test
    public void test() throws Exception {
        String methodName = "()";
        int st = methodName.indexOf('(');
        int end = methodName.length();
        System.out.println((st + 1) + "" + (end - 1));
        String substring = methodName.substring(st + 1, end - 1);

        System.out.println(substring.equals(""));
    }
}
