@file:Suppress("SameParameterValue")

package com.buildkite.test.collector.android.tracer.environment

import com.buildkite.test.collector.android.TestDataUploader

/**
 * Creates a [TestDataUploader] instance for unit tests, fetching configuration from environment variables.
 */
fun configureUnitTestUploader() = TestDataUploader(
    testSuiteApiToken = getStringEnvironmentValue(name = EnvironmentValues.BUILDKITE_ANALYTICS_TOKEN),
    isDebugEnabled = getBooleanEnvironmentValue(name = EnvironmentValues.BUILDKITE_ANALYTICS_DEBUG_ENABLED)
)

/**
 * Creates a [TestDataUploader] instance for instrumented tests with the given [apiToken] and [isDebugEnabled] flag, filtering out 'null' tokens.
 */
fun configureInstrumentedTestUploader(
    apiToken: String,
    isDebugEnabled: Boolean
) = TestDataUploader(
    testSuiteApiToken = apiToken.takeIf { token -> token != "null" },
    isDebugEnabled = isDebugEnabled
)

private fun getStringEnvironmentValue(name: String): String? {
    return System.getenv(name)
}

private fun getBooleanEnvironmentValue(name: String): Boolean {
    return System.getenv(name)?.let { value -> value.toBoolean() } ?: false
}
