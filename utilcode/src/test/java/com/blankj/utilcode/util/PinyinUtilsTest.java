package com.blankj.utilcode.util;

import org.junit.Before;
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

    @Before
    public void setUp() throws Exception {
        PinyinUtils.ccs2Pinyin("");
    }

    @Test
    public void getPinyinFirstLetter() throws Exception {
        System.out.println("单: " + PinyinUtils.getPinyinFirstLetter("单"));
        System.out.println("元: " + PinyinUtils.getPinyinFirstLetter("元"));
        System.out.println("測: " + PinyinUtils.getPinyinFirstLetter("測"));
        System.out.println("試: " + PinyinUtils.getPinyinFirstLetter("試"));
    }

    @Test
    public void getPinyinFirstLetters() throws Exception {
        System.out.println("單元測試: " + PinyinUtils.getPinyinFirstLetters("單元測試", ","));
    }

    @Test
    public void ccs2Pinyin() throws Exception {
        long t = System.currentTimeMillis();
        System.out.println("已初始化的汉字转拼音用时测试: " + PinyinUtils.ccs2Pinyin("已初始化的汉字转拼音用时测试", " "));
        System.out.printf("用时: %dms\n", System.currentTimeMillis() - t);
    }

    @Test
    public void getSurnamePinyin() throws Exception {
        System.out.println("澹台: " + PinyinUtils.getSurnamePinyin("澹台"));
        System.out.println("尉迟: " + PinyinUtils.getSurnamePinyin("尉迟"));
        System.out.println("万俟: " + PinyinUtils.getSurnamePinyin("万俟"));
        System.out.println("单于: " + PinyinUtils.getSurnamePinyin("单于"));
        String surnames = "乐乘乜仇会便区单参句召员宓弗折曾朴查洗盖祭种秘繁缪能蕃覃解谌适都阿难黑";
        int size = surnames.length();
        long t = System.currentTimeMillis();
        for (int i = 0; i < size; ++i) {
            String surname = String.valueOf(surnames.charAt(i));
            System.out.printf("%s 正确: %-6s 首字母: %-6s 错误: %-6s\n",
                    surname,
                    PinyinUtils.getSurnamePinyin(surname),
                    PinyinUtils.getSurnameFirstLetter(surname),
                    PinyinUtils.ccs2Pinyin(surname));
        }
        System.out.printf("用时: %dms\n", System.currentTimeMillis() - t);
    }
}