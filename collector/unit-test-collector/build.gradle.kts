plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm")
    alias(libs.plugins.maven.publish)
}

dependencies {
    implementation(projects.collector.testDataUploader)
}

gradlePlugin {
    plugins {
        create("unitTestCollectorPlugin") {
            id = "com.buildkite.test-collector-android.unit-test-collector-plugin"
            implementationClass = "com.buildkite.test.collector.android.UnitTestCollectorPlugin"
            displayName = "Unit Test Collector Plugin"
            description = "Android Unit Test Collector Gradle Plugin."
        }
    }
}
