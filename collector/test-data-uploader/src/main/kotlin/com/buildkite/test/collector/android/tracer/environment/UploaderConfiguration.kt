@file:Suppress("SameParameterValue")

package com.buildkite.test.collector.android.tracer.environment

import com.buildkite.test.collector.android.TestDataUploader

fun configureUnitTestUploader() = TestDataUploader(
    testSuiteApiToken = getStringEnvironmentValue(name = EnvironmentValues.BUILDKITE_ANALYTICS_TOKEN),
    isDebugEnabled = getBooleanEnvironmentValue(name = EnvironmentValues.BUILDKITE_ANALYTICS_DEBUG_ENABLED)
)

fun configureInstrumentedTestUploader(
    apiToken: String,
    isDebugEnabled: Boolean
) = TestDataUploader(
    testSuiteApiToken = apiToken,
    isDebugEnabled = isDebugEnabled
)

private fun getStringEnvironmentValue(name: String): String {
    return System.getenv(name)
}

private fun getBooleanEnvironmentValue(name: String): Boolean {
    return System.getenv(name)?.let { value -> value.toBoolean() } ?: false
}
