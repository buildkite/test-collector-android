package com.buildkite.test.collector.android.util

import android.os.Bundle
import com.buildkite.test.collector.android.BuildKiteTestDataUploader
import com.buildkite.test.collector.android.TestDataUploader
import com.buildkite.test.collector.android.tracer.environment.BuildkiteEnvironmentValues

internal object InstrumentedTestCollectorUtils {

    /**
     * Creates a [TestDataUploader] instance for instrumented tests.
     */
    fun configureTestUploader(arguments: Bundle): TestDataUploader {
        return BuildKiteTestDataUploader(
            testSuiteApiToken = arguments.getStringEnvironmentValue(
                BuildkiteEnvironmentValues.BUILDKITE_ANALYTICS_TOKEN
            ),
            isDebugEnabled = arguments.getBooleanEnvironmentValue(
                BuildkiteEnvironmentValues.BUILDKITE_ANALYTICS_DEBUG_ENABLED
            )
        )
    }

    private fun Bundle.getStringEnvironmentValue(key: String): String? =
        getString(key)?.takeIf { value -> value.isNotBlank() && value != "null" }

    private fun Bundle.getBooleanEnvironmentValue(key: String): Boolean =
        getString(key)?.toBoolean() ?: false
}
