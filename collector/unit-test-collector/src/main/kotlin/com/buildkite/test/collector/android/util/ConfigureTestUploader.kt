package com.buildkite.test.collector.android.util

import com.buildkite.test.collector.android.BuildKiteTestDataUploader
import com.buildkite.test.collector.android.TestDataUploader
import com.buildkite.test.collector.android.tracer.environment.BuildkiteEnvironmentValues

/**
 * Configures a [TestDataUploader] instance for unit tests.
 */
fun configureUnitTestUploader() = BuildKiteTestDataUploader(
    testSuiteApiToken = getStringEnvironmentValue(key = BuildkiteEnvironmentValues.BUILDKITE_ANALYTICS_TOKEN),
    isDebugEnabled = getBooleanEnvironmentValue(key = BuildkiteEnvironmentValues.BUILDKITE_ANALYTICS_DEBUG_ENABLED)
)

private fun getStringEnvironmentValue(key: String): String? =
    System.getenv(key)?.takeIf { value -> value.isNotBlank() && value != "null" }

private fun getBooleanEnvironmentValue(key: String): Boolean =
    System.getenv(key)?.toBoolean() ?: false
