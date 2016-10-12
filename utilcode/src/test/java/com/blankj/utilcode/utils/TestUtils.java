package com.blankj.utilcode.utils;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.blankj.utilcode.utils.TimeUtils.milliseconds2String;
import static com.google.common.truth.Truth.assertThat;

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

    public static final char SEP = File.separatorChar;

    public static final String BASEPATH = System.getProperty("user.dir")
            + SEP + "src" + SEP + "test" + SEP + "res" + SEP;

    public static Context getContext() {
        return RuntimeEnvironment.application;
    }

    @Test
    public void readme2Eng() throws Exception {
        File readme = new File(new File(System.getProperty("user.dir")).getParent() + SEP + "README-CN.md");
        File readmeEng = new File(new File(System.getProperty("user.dir")).getParent() + SEP + "README.md");
        List<String> list = FileUtils.readFile2List(readme, "UTF-8");
        StringBuilder sb = new StringBuilder();
        for (String line : list) {
            if (line.length() >= 3 && line.startsWith("> -") && line.contains("Utils")) {
                String utilsName = line.substring(line.indexOf("[") + 1, line.indexOf("Utils"));
                sb.append("> - **About ").append(utilsName).append(line.substring(line.indexOf("→")));
            } else if (line.length() >= 3 && line.startsWith(">  ")) {
                sb.append(">  - ").append(line.substring(line.indexOf("*")));
            } else if (line.length() >= 3 && line.startsWith("## ")) {
                sb.append("## Android developers should collect the following utils")
                        .append("\r\n").append("**[中文版README][readme.cn]**");
            } else if (line.length() >= 1 && line.startsWith("为")) {
                sb.append("Directory is shown below：  ");
            } else if (line.length() >= 2 && line.startsWith("**做")) {
                sb.append("**I'm so sorry for that the code is annotated with Chinese.**");
            } else {
                sb.append(line);
            }
            sb.append("\r\n");
        }
        sb.append("\r\n[readme.cn]: https://github.com/Blankj/AndroidUtilCode/blob/master/README-CN.md");
        FileUtils.writeFileFromString(readmeEng, sb.toString(), false);
    }
}
