plugins {
    `kotlin-dsl`
}
group = "com.buildkite.test.collector.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("staticCodeAnalysis") {
            id = "buildkite.static-code-analysis"
            implementationClass = "StaticCodeAnalysisConventionPlugin"
        }
    }
}
