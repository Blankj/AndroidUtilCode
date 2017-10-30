package com.blankj.utilcode.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;
import java.util.List;

import static com.blankj.utilcode.util.TestConfig.FILE_SEP;
import static com.blankj.utilcode.util.TestConfig.LINE_SEP;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/21
 *     desc  : 单元测试工具类
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TestUtils {

    public static void init() {
        Utils.init(RuntimeEnvironment.application);
    }

    private static final String LONG_SPACE = "                                        ";

//    @Test
    public void readme2Eng() throws Exception {
        String rootPath = new File(System.getProperty("user.dir")).getAbsolutePath() + FILE_SEP;
        File readmeCN = new File(rootPath + "utilcode" + FILE_SEP + "README-CN.md");
        File readme = new File(rootPath + "utilcode" + FILE_SEP + "README.md");
        readmeOfUtilCode(readmeCN, readme);

        readmeCN = new File(rootPath + "subutil" + FILE_SEP + "README-CN.md");
        readme = new File(rootPath + "subutil" + FILE_SEP + "README.md");
        readmeOfSubUtil(readmeCN, readme);
    }


    private void readmeOfUtilCode(File readmeCN, File readme) throws Exception {
        formatCN(readmeCN);
        List<String> lines = FileIOUtils.readFile2List(readmeCN, "UTF-8");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            if (line.contains("* ###")) {
                String utilsName = line.substring(line.indexOf("[") + 1, line.indexOf("Utils"));
                sb.append("* ### About ").append(utilsName).append(line.substring(line.indexOf("→")));
            } else if (line.contains(": ") && !line.contains("[")) {
                sb.append(line.substring(0, line.indexOf(':')).trim());
            } else {
                sb.append(line);
            }
            sb.append(LINE_SEP);
        }
        FileIOUtils.writeFileFromString(readme, sb.toString());
    }


    private void readmeOfSubUtil(File readmeCN, File readme) throws Exception {
        formatCN(readmeCN);
        List<String> lines = FileIOUtils.readFile2List(readmeCN, "UTF-8");
        StringBuilder sb = new StringBuilder("## How to use" + LINE_SEP
                + LINE_SEP +
                "You should copy the following classes which you want to use in your project." + LINE_SEP);
        for (int i = 3, len = lines.size(); i < len; ++i) {
            String line = lines.get(i);
            if (line.contains("* ###")) {
                String utilsName = line.substring(line.indexOf("[") + 1, line.indexOf("Utils"));
                sb.append("* ### About ").append(utilsName).append(line.substring(line.indexOf("→")));
            } else if (line.contains(": ") && !line.contains("[")) {
                sb.append(line.substring(0, line.indexOf(':')).trim());
            } else {
                sb.append(line);
            }
            sb.append(LINE_SEP);
        }
        FileIOUtils.writeFileFromString(readme, sb.toString());
    }

    public void formatCN(File file) throws Exception {
        List<String> list = FileIOUtils.readFile2List(file, "UTF-8");
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = list.size(); i < len; ++i) {
            String line = list.get(i);
            if (line.contains("* ###")) {
                sb.append(line).append(LINE_SEP);
                int maxLen = 0;
                line = list.get(++i);
                // get the max length of space
                for (int j = i; !line.equals(""); line = list.get(++j)) {
                    if (line.equals("```")) continue;
                    maxLen = Math.max(maxLen, line.replace(" ", "").replace(",", ", ").indexOf(':'));
                }
                line = list.get(i);
                for (; !line.equals(""); line = list.get(++i)) {
                    if (line.equals("```")) {
                        sb.append("```");
                    } else {
                        String noSpaceLine = line.replace(" ", "");
                        int spaceLen = maxLen - line.replace(" ", "").replace(",", ", ").indexOf(':');
                        sb.append(noSpaceLine.substring(0, noSpaceLine.indexOf(':')).replace(",", ", "))
                                .append(LONG_SPACE.substring(0, spaceLen))// add the space
                                .append(": ")
                                .append(noSpaceLine.substring(noSpaceLine.indexOf(':') + 1));
                    }
                    sb.append(LINE_SEP);
                }
            } else {
                sb.append(line);
            }
            sb.append(LINE_SEP);
        }
        FileIOUtils.writeFileFromString(file, sb.toString());
    }

    @Test
    public void test() throws Exception {

    }

}