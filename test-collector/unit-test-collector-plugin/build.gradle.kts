plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm")
    id("com.vanniktech.maven.publish")
}

dependencies {
    implementation(project(":test-collector:test-data-uploader"))

    api(gradleApi())
}