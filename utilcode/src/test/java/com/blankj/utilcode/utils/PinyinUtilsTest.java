package com.blankj.utilcode.utils;

import org.junit.Test;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/11/16
 *     desc  : PinyinUtils单元测试
 * </pre>
 */
public class PinyinUtilsTest {

    @Test
    public void getPinyinFirstLetter() throws Exception {
        System.out.println(PinyinUtils.getPinyinFirstLetter("单"));
        System.out.println(PinyinUtils.getPinyinFirstLetter("元"));
        System.out.println(PinyinUtils.getPinyinFirstLetter("測"));
        System.out.println(PinyinUtils.getPinyinFirstLetter("試"));
    }

    @Test
    public void getPinyinFirstLetters() throws Exception {
        System.out.println(PinyinUtils.getPinyinFirstLetters("單元測試",","));
    }

    @Test
    public void ccs2Pinyin() throws Exception {
        long t = System.currentTimeMillis();
        System.out.println(PinyinUtils.ccs2Pinyin("未初始化的汉字转拼音用时测试", " "));
        System.out.printf("用时: %dms\n", System.currentTimeMillis() - t);
        t = System.currentTimeMillis();
        System.out.println(PinyinUtils.ccs2Pinyin("已初始化的汉字转拼音用时测试", " "));
        System.out.printf("用时: %dms\n", System.currentTimeMillis() - t);
    }

    @Test
    public void surname2Pinyin() throws Exception {
        System.out.println(PinyinUtils.surname2Pinyin("澹台"));
        System.out.println(PinyinUtils.surname2Pinyin("尉迟"));
        System.out.println(PinyinUtils.surname2Pinyin("万俟"));
        System.out.println(PinyinUtils.surname2Pinyin("单于"));
        String str = "乐乘乜仇会便区单参句召员宓弗折曾朴查洗盖祭种秘繁缪能蕃覃解谌适都阿难黑";
        int size = str.length();
        System.out.println(size);
        long t = System.currentTimeMillis();
        for (int i = 0; i < size; ++i) {
            String s = String.valueOf(str.charAt(i));
            System.out.println(s + " 正确: "
                    + PinyinUtils.surname2Pinyin(s) + " 错误: "
                    + PinyinUtils.ccs2Pinyin(s));
        }
        System.out.printf("用时: %dms\n", System.currentTimeMillis() - t);
    }

    @Test
    public void cn2Pinyin() throws Exception {
        System.out.println(PinyinUtils.ccs2Pinyin("测试中文转拼音"));
        System.out.println(PinyinUtils.ccs2Pinyin("测试中文转拼音", " "));
    }
}