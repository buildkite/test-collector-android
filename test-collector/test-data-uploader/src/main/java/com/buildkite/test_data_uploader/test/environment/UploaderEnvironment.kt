@file:Suppress("SameParameterValue")

package com.buildkite.test_data_uploader.test.environment

import com.buildkite.test_data_uploader.TestDataUploader
import com.buildkite.test_data_uploader.util.EnvironmentValues

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
