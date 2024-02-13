@file:Suppress("UnstableApiUsage")

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.vanniktech.maven.publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.google.gson)

    testImplementation(libs.testing.junit)
}
