package com.blankj.utilcode.utils;

import org.junit.Test;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/11/16
 *     desc  :
 * </pre>
 */
public class PinyinUtilsTest {
    @Test
    public void getPinyinFirstLetter() throws Exception {
        System.out.println(PinyinUtils.getPinyinFirstLetter("杰"));
    }

    @Test
    public void cn2Pinyin() throws Exception {
        System.out.println(PinyinUtils.ccs2Pinyin("测试中文转拼音"));
        System.out.println(PinyinUtils.ccs2Pinyin("测试中文转拼音", " "));
    }
}