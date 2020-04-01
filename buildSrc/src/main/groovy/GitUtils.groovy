import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.invocation.Gradle

import java.text.SimpleDateFormat

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/16
 *     desc  :
 * </pre>
 */
class GitUtils {

    private static Project rootProject;

    static void init(Gradle gradle) {
        rootProject = gradle.rootProject
        addGitHelpTask()
    }

    static def addGitHelpTask() {
        rootProject.task("gitHelp").doLast {
            def commands = [
                    "                 ############## input command code #################",
                    "                 #          [1] Git Push                           #",
                    "                 #          [2] Git Push And Merge to Master       #",
                    "                 #          [3] Git New Branch                     #",
                    "                 #          [0] exit                               #",
                    "                 ###################################################",
            ]
            String commandTips = String.join(System.getProperty("line.separator"), commands)
            while (true) {
                GLog.l(commandTips)
                Scanner scanner = new Scanner(System.in)
                def input = scanner.next()
                GLog.l(input)
                switch (input) {
                    case "1":
                        gitPush()
                        break
                    case "2":
                        gitPushAndMerge2Master()
                        break
                    case "3":
                        gitNewBranch()
                        break
                    case "0":
                        return
                }
            }
        }
    }

    static void gitPush() {
        String branchName = getGitBranch()
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd")
        String date = simpleDateFormat.format(new Date())
        exeCmd(
                "git add -A",
                "git commit -m \"see $date log\"",
                "git push origin $branchName"
        )
    }

    static void gitPushAndMerge2Master() {
        String branchName = getGitBranch()
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd")
        String date = simpleDateFormat.format(new Date())
        exeCmd(
                "git add -A",
                "git commit -m \"see $date log\"",
                "git push origin $branchName",
                "git checkout master",
                "git merge $branchName",
                "git push origin master",
                "git checkout $branchName"
        )
    }

    static void gitNewBranch() {
        exeCmd(
                "git checkout master",
                "git checkout -b ${Config.versionName}",
                "git push origin ${Config.versionName}:${Config.versionName}",
        )
    }

    private static def getGitBranch() {
        return exeCmd("git symbolic-ref --short -q HEAD")
    }

    private static def exeCmd(String... cmds) {
        String output = ""
        for (def cmd in cmds) {
            output = _exeCmd(cmd)
        }
        return output
    }

    private static def _exeCmd(String cmd) {
        def output = new StringBuilder()
        GLog.l("Execute command: ${cmd}")
        def cmdResult = ShellUtils.execCmd(cmd)
        GLog.l("$cmdResult")
        return cmdResult.successMsg
    }
}
// ./gradlew gitHelp
