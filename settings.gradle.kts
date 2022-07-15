@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenLocal()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenLocal()
        mavenCentral()
    }
}
include(":sample")
include(":test-collector")
include(":test-collector:instrumented-test-collector-library")
include(":test-collector:unit-test-collector-plugin")
include(":test-collector:test-data-uploader")
