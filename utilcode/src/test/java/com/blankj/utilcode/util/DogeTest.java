package com.blankj.utilcode.util;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/11/23
 *     desc  : 生成Doge的README
 * </pre>
 */
public class DogeTest {

    private static List<File> dogeFiles = new ArrayList<>();
    private static int[]      indexes   = {0, 50, 100, 150, 200, 300, 400, 450, 500, 550, 600, 700};

    private static int bitNum = 3;

    static {
        dogeFiles.add(new File("F:/MyGithub/doge-expression/_00_000_049_SingleRun"));
        dogeFiles.add(new File("F:/MyGithub/doge-expression/_01_050_099_Tear"));
        dogeFiles.add(new File("F:/MyGithub/doge-expression/_02_100_149_Lines"));
        dogeFiles.add(new File("F:/MyGithub/doge-expression/_03_150_199_MoreRun"));
        dogeFiles.add(new File("F:/MyGithub/doge-expression/_04_200_299_ToLeft"));
        dogeFiles.add(new File("F:/MyGithub/doge-expression/_05_300_399_ToRight"));
        dogeFiles.add(new File("F:/MyGithub/doge-expression/_06_400_449_Big"));
        dogeFiles.add(new File("F:/MyGithub/doge-expression/_07_450_499_Hug"));
        dogeFiles.add(new File("F:/MyGithub/doge-expression/_08_500_549_Wang"));
        dogeFiles.add(new File("F:/MyGithub/doge-expression/_09_550_599_ToWall"));
        dogeFiles.add(new File("F:/MyGithub/doge-expression/_10_600_699_OtherGif"));
        dogeFiles.add(new File("F:/MyGithub/doge-expression/_11_700_799_OtherJpg"));
    }


    @Test
    public void generateDogeMD() throws Exception {
        bitNum = 4;
        renameDogeNames();
        bitNum = 3;
        renameDogeNames();
        StringBuilder sb = new StringBuilder();
        sb.append("卡通doge的表情大集合，喜爱doge的朋友的福利到了，花了很多的时间整理出来的狗东西，就被你们这么轻而易举地拿走了，现附上QQ表情包链接[doge-expression.eif](https://raw.githubusercontent.com/Blankj/doge-expression/master/doge-expression.eif)（进去点击Download即可）\n\n");
        sb.append("下面展示doge各种姿势，请系好安全带，开车啦，滴滴滴~~\n\n");
        for (int i = 0; i < dogeFiles.size(); ++i) {
            List<File> files = FileUtils.listFilesInDir(dogeFiles.get(i));
            for (File f : files) {
                String name = f.getName();
                sb.append("![")
                        .append(name)
                        .append("]")
                        .append("(https://github.com/Blankj/doge-expression/raw/master/")
                        .append(f.getParentFile().getName())
                        .append("/")
                        .append(name)
                        .append(")  \n");
            }
        }
//        System.out.println(sb.toString());
        FileUtils.writeFileFromString("F:/MyGithub/doge-expression/README.md", sb.toString(), false);
    }

    @Test
    public void renameDogeNames() throws Exception {
        for (int i = 0; i < dogeFiles.size(); ++i) {
            List<File> files = FileUtils.listFilesInDir(dogeFiles.get(i));
            int index = indexes[i];
            for (File f : files) {
                String name = f.getName();
                String rename = String.format(
                        Locale.getDefault(),
                        "%s%0" + bitNum + "d%s",
                        f.getParent() + File.separator,
                        index++,
                        name.substring(name.length() - 4)
                );
                f.renameTo(new File(rename));
            }
        }
    }
}
