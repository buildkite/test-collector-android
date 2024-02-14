pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "TestCollectorSDK"

include(":example")

include(":collector")
include(":collector:instrumented-test-collector")
include(":collector:unit-test-collector")
include(":collector:test-data-uploader")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")