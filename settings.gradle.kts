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
include(":collector")
include(":collector:instrumented-test-collector")
include(":collector:unit-test-collector")
include(":collector:test-data-uploader")
enableFeaturePreview("VERSION_CATALOGS")