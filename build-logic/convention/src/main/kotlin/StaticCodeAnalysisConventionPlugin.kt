import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class StaticCodeAnalysisConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("io.gitlab.arturbosch.detekt")
            }

            fun Detekt.commonDetektConfiguration() {
                parallel = true
                buildUponDefaultConfig = true
                setSource(file(projectDir))
                config.setFrom(file("$rootDir/support/vendor/detekt/detekt.yml"))
                include("**/*.kt")
                include("**/*.kts")
                exclude("**/resources/**")
                exclude("**/build/**")
                reports {
                    xml.required.set(false)
                    html.required.set(true)
                }
            }

            tasks.register(TASK_DETEKT_ALL, Detekt::class.java) {
                description = "Run lint check for whole project"
                commonDetektConfiguration()
            }

            tasks.register(TASK_DETEKT_FORMAT, Detekt::class.java) {
                description = "Format lint errors for whole project"
                autoCorrect = true
                commonDetektConfiguration()
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add(
                    configurationName = "detektPlugins",
                    dependencyNotation = libs.findLibrary("detekt.formatting").get()
                )
            }
        }
    }

    private companion object {
        const val TASK_DETEKT_ALL = "detektAll"
        const val TASK_DETEKT_FORMAT = "detektFormat"
    }
}
