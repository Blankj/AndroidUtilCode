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

    private static String sCurBranchName;

    static void init(Gradle gradle) {
        gradle.rootProject(new Action<Project>() {
            @Override
            void execute(Project project) {
                sCurBranchName = getGitBranch()
                addGitPushTask(project)
                addGitPushAndMerge2MasterTask(project)
                addGitNewBranchTask(project)
            }
        })
    }

    static def getGitBranch() {
        return ShellUtils.execCmd('git symbolic-ref --short -q HEAD').successMsg
    }

    static void addGitPushTask(Project project) {
        project.task("gitPush").doLast {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd")
            String date = simpleDateFormat.format(new Date())
            GLog.d(ShellUtils.execCmd([
                    "git add -A",
                    "git commit -m \"see $date log\"",
                    "git push origin $sCurBranchName"
            ] as String[]))
        }
    }

    static void addGitPushAndMerge2MasterTask(Project project) {
        project.task("gitPushAndMerge2Master").doLast {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd")
            String date = simpleDateFormat.format(new Date())
            GLog.d(ShellUtils.execCmd([
                    "git add -A",
                    "git commit -m \"see $date log\"",
                    "git push origin $sCurBranchName",
                    "git checkout master",
                    "git merge $sCurBranchName",
                    "git push origin master",
                    "git checkout $sCurBranchName",
            ] as String[]))
        }
    }

    static void addGitNewBranchTask(Project project) {
        project.task("gitNewBranch").doLast {
            GLog.d(ShellUtils.execCmd([
                    "git checkout master",
                    "git checkout -b ${Config.versionName}",
                    "git push origin ${Config.versionName}:${Config.versionName}",
            ] as String[]))
        }
    }
}
// ./gradlew gitPush
// ./gradlew gitPushAndMerge2Master
// ./gradlew gitNewBranch
