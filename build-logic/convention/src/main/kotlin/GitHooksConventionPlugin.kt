import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Exec

class GitHooksConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            tasks.register(TASK_COPY_HOOKS, Copy::class.java) {
                from("$rootDir/support/scripts/git") {
                    include("pre-push")
                }
                into("$rootDir/.git/hooks")
            }
            tasks.register(TASK_INSTALL_HOOKS, Exec::class.java) {
                workingDir(rootDir)
                outputs.dir("$rootDir/.git/hooks")
                commandLine("chmod")
                args("-R", "+x", ".git/hooks")
                dependsOn(tasks.named(TASK_COPY_HOOKS))
            }

            tasks.getByPath(":example:preBuild").dependsOn(tasks.named(TASK_INSTALL_HOOKS))
        }
    }

    private companion object {
        const val TASK_COPY_HOOKS = "copyGitHooks"
        const val TASK_INSTALL_HOOKS = "installGitHooks"
    }
}
