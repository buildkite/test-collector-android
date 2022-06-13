buildscript {
    repositories {
        mavenLocal()
    }

    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.20.0")
        // Uncomment below plugin implementation in order to use the plugin published on local maven repo
        // Note: Since Github server doesn't recognise repository path, MUST Comment it out before creating PR
        classpath("com.buildkite.test-collector-android:unit-test-collector-plugin:0.1.0")
    }
}

plugins {
    id("com.android.application") version("7.2.1") apply false
    id("com.android.library") version("7.2.1") apply false
    id("org.jetbrains.kotlin.android") version("1.6.21") apply false
    id("org.jetbrains.kotlin.jvm") version "1.6.21" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
