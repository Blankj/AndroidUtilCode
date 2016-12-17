package com.blankj.utilcode.utils;

import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/11/23
 *     desc  : 生成Doge的README
 * </pre>
 */
public class DogeTest {

    @Test
    public void generateDogeMD() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("卡通doge的表情大集合，喜爱doge的朋友的福利到了，花了大半天的时间整理出来的狗东西，就被你们这么轻而易举地拿走了，现附上QQ表情包链接[doge-expression.eif](https://github.com/Blankj/doge-expression/blob/master/doge-expression.eif)（进去点击Download即可）\n\n");
        sb.append("下面展示doge各种姿势，请系好安全带，开车啦，滴滴滴~~\n\n");
        File file = new File("F:/MyGithub/doge-expression/expression");
        List<File> files = FileUtils.listFilesInDir(file);
        for (File f : files) {
            String name = f.getName();
            sb.append("![")
                    .append(name)
                    .append("]")
                    .append("(https://github.com/Blankj/doge-expression/raw/master/expression/")
                    .append(name)
                    .append(")  \n");
        }
        FileUtils.writeFileFromString("F:/MyGithub/doge-expression/README.md", sb.toString(), false);
    }
}
