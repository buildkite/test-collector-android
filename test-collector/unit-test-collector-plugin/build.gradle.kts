plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":test-collector:test-data-uploader"))

    api(gradleApi())
}