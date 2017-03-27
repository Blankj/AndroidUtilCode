package com.blankj.utilcode.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/21
 *     desc  : 单元测试工具类
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TestUtils {

    static final char SEP = File.separatorChar;

    static final String BASEPATH = System.getProperty("user.dir") + SEP + "src" + SEP + "test" + SEP + "res" + SEP;

    public static void init() {
        Utils.init(RuntimeEnvironment.application);
    }

    @Test
    public void readme2Eng() throws Exception {
        formatCN();
        File readmeCN = new File(new File(System.getProperty("user.dir")).getAbsolutePath() + SEP + "README-CN.md");
        File readmeEng = new File(new File(System.getProperty("user.dir")).getAbsolutePath() + SEP + "README.md");
        List<String> list = FileUtils.readFile2List(readmeCN, "UTF-8");
        StringBuilder sb = new StringBuilder("# Android developers should collect the following utils\r\n\r\n" +
                "## [README of Chinese][readme-cn.md]\r\n\r\n" +
                "Directory is shown below:\r\n\r\n");
        List<String> lines = list.subList(6, list.size());
        for (String line : lines) {
            if (line.contains("> -") && line.contains("Utils")) {
                String utilsName = line.substring(line.indexOf("[") + 1, line.indexOf("Utils"));
                sb.append("> - **About ").append(utilsName).append(line.substring(line.indexOf("→")));
            } else if (line.contains(": ") && !line.contains("[")) {
                sb.append(line.substring(0, line.indexOf(':')).trim());
            } else if (line.contains("* 做")) {
                sb.append("**I'm so sorry for that the code is annotated with Chinese.**");
            } else if (line.contains("* QQ") || line.contains("* 我的")) {
                continue;
            } else {
                sb.append(line);
            }
            sb.append("\r\n");
        }
        FileUtils.writeFileFromString(readmeEng, sb.toString(), false);
    }

    @Test
    public void formatCN() throws Exception {
        File readmeCN = new File(new File(System.getProperty("user.dir")).getAbsolutePath() + SEP + "README-CN.md");
        List<String> list = FileUtils.readFile2List(readmeCN, "UTF-8");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(list.get(i)).append("\r\n");
        }
        String space = " ";
        for (int i = 4, len = list.size(); i < len; ++i) {
            String line = list.get(i);
            if (line.contains("> -") && line.contains("Utils")) {
                sb.append(line).append("\r\n");
                int maxLen = 0;
                line = list.get(++i);
                // 获取需填充最大空格数
                for (int j = i; !line.equals(""); line = list.get(++j)) {
                    if (line.equals(" ```")) continue;
                    maxLen = Math.max(maxLen, line.replace(" ", "").replace(",", ", ").indexOf(':'));
                }
                line = list.get(i);
                for (; !line.equals(""); line = list.get(++i)) {
                    if (line.equals(" ```")) {
                        sb.append(" ```").append("\r\n");
                        continue;
                    }
                    String noSpaceLine = line.replace(" ", "");
                    int l = maxLen - line.replace(" ", "").replace(",", ", ").indexOf(':');
                    String spaces = "";
                    for (int j = 0; j < l; j++) {
                        spaces += space;
                    }
                    String temp = noSpaceLine.substring(0, noSpaceLine.indexOf(':')) + spaces + ": " + noSpaceLine.substring(noSpaceLine.indexOf(':') + 1) + "\r\n";
                    sb.append(temp.replace(",", ", "));
                }
            } else {
                sb.append(line);
            }
            sb.append("\r\n");
        }
        FileUtils.writeFileFromString(readmeCN, sb.toString(), false);
    }

    @Test
    public void test() throws Exception {
        System.out.println(System.getProperty("user.dir"));
    }
}