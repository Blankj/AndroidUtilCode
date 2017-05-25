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
 *     time  : 2016/08/21
 *     desc  : 单元测试工具类
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TestUtils {

    static final String FILE_SEP = System.getProperty("file.separator");

    static final String LINE_SEP = System.getProperty("line.separator");

    static final String TEST_PATH;

    static {
        String projectPath = System.getProperty("user.dir");
        if (!projectPath.contains("utilcode")) {
            projectPath += FILE_SEP + "utilcode";
        }
        TEST_PATH = projectPath + FILE_SEP + "src" + FILE_SEP + "test" + FILE_SEP + "res";
    }

    public static void init() {
        Utils.init(RuntimeEnvironment.application);
    }

//    @Test
    public void readme2Eng() throws Exception {
        formatCN();
        File readmeCN = new File(new File(System.getProperty("user.dir")).getAbsolutePath() + FILE_SEP + "README-CN.md");
        File readmeEng = new File(new File(System.getProperty("user.dir")).getAbsolutePath() + FILE_SEP + "README.md");
        List<String> list = FileIOUtils.readFile2List(readmeCN, "UTF-8");
        StringBuilder sb = new StringBuilder("# :fire: Android developers should collect the following utils" + LINE_SEP + LINE_SEP +
                "[![auc][aucsvg]][auc] [![api][apisvg]][api] [![build][buildsvg]][build] [![License][licensesvg]][license]" + LINE_SEP + LINE_SEP +
                "## [README of Chinese][readme-cn.md]" + LINE_SEP + LINE_SEP +
                "## API" + LINE_SEP + LINE_SEP);
        List<String> lines = list.subList(8, list.size());
        for (String line : lines) {
            if (line.contains("* ###")) {
                if (line.contains("Utils")) {
                    String utilsName = line.substring(line.indexOf("[") + 1, line.indexOf("Utils"));
                    sb.append("* ### About ").append(utilsName).append(line.substring(line.indexOf("→")));
                } else {
                    sb.append("* ### About Log→[update_log.md][update_log.md]");
                }
            } else if (line.contains(": ") && !line.contains("[")) {
                sb.append(line.substring(0, line.indexOf(':')).trim());
            } else if (line.contains("* 做")) {
                sb.append("* **I'm so sorry for that the code is annotated with Chinese.**");
            } else if (line.contains("* QQ") || line.contains("* 我的")) {
                continue;
            } else {
                sb.append(line);
            }
            sb.append(LINE_SEP);
        }
        FileIOUtils.writeFileFromString(readmeEng, sb.toString(), false);
    }

    public void formatCN() throws Exception {
        File readmeCN = new File(new File(System.getProperty("user.dir")).getAbsolutePath() + FILE_SEP + "README-CN.md");
        List<String> list = FileIOUtils.readFile2List(readmeCN, "UTF-8");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(list.get(i)).append(LINE_SEP);
        }
        String space = " ";
        for (int i = 4, len = list.size(); i < len; ++i) {
            String line = list.get(i);
            if (line.contains("* ###") && line.contains("Utils")) {
                sb.append(line).append(LINE_SEP);
                int maxLen = 0;
                line = list.get(++i);
                // 获取需填充最大空格数
                for (int j = i; !line.equals(""); line = list.get(++j)) {
                    if (line.equals("```")) continue;
                    maxLen = Math.max(maxLen, line.replace(" ", "").replace(",", ", ").indexOf(':'));
                }
                line = list.get(i);
                for (; !line.equals(""); line = list.get(++i)) {
                    if (line.equals("```")) {
                        sb.append("```").append(LINE_SEP);
                        continue;
                    }
                    String noSpaceLine = line.replace(" ", "");
                    int l = maxLen - line.replace(" ", "").replace(",", ", ").indexOf(':');
                    String spaces = "";
                    for (int j = 0; j < l; j++) {
                        spaces += space;
                    }
                    String temp = noSpaceLine.substring(0, noSpaceLine.indexOf(':')) + spaces + ": " + noSpaceLine.substring(noSpaceLine.indexOf(':') + 1) + LINE_SEP;
                    sb.append(temp.replace(",", ", "));
                }
            } else {
                sb.append(line);
            }
            sb.append(LINE_SEP);
        }
        FileIOUtils.writeFileFromString(readmeCN, sb.toString(), false);
    }

    @Test
    public void test() throws Exception {
    }
}