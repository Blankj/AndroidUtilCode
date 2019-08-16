import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
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
            }
        })
    }

    static def getGitBranch() {
        return ShellUtils.execCmd('git symbolic-ref --short -q HEAD').successMsg
    }

    static void addGitPushTask(Project project) {
        project.task("gitPush", new Action<Task>() {
            @Override
            void execute(Task task) {
                task.doLast {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd")
                    String date = simpleDateFormat.format(new Date())
                    GLog.d(ShellUtils.execCmd([
                            "git add -A",
                            "git commit -m \"see $date log\"",
                            "git push origin $sCurBranchName"
                    ] as String[]))
                }
            }
        })
    }

    static void addGitPush2MasterTask(Project project) {
        project.task("gitPush2Master", new Action<Task>() {
            @Override
            void execute(Task task) {
                task.doLast {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd")
                    String date = simpleDateFormat.format(new Date())
                    GLog.d(ShellUtils.execCmd([
                            "git add -A",
                            "git commit -m \"see $date log\"",
                            "git push origin $sCurBranchName"
                    ] as String[]))
                }
            }
        })
    }
}
