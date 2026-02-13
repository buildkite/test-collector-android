package com.buildkite.test.collector.android

/**
 * Gradle extension for configuring Buildkite Test Analytics.
 *
 * Usage in build.gradle.kts:
 * ```
 * buildkiteTestAnalytics {
 *     tags["environment"] = "production"
 *     tags["language.name"] = "kotlin"
 * }
 * ```
 */
open class UnitTestCollectorExtension {
    val tags: MutableMap<String, String> = linkedMapOf()
}
