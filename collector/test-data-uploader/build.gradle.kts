plugins {
    id("org.jetbrains.kotlin.jvm")
    alias(libs.plugins.maven.publish)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)

    testImplementation(libs.testing.junit)
}
