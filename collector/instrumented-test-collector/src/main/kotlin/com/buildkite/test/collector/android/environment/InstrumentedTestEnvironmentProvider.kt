package com.buildkite.test.collector.android.environment

import android.os.Bundle
import com.buildkite.test.collector.android.tracer.environment.BaseTestEnvironmentProvider

/**
 * Provides environment values for instrumented tests by fetching from Android [Bundle] arguments.
 */
internal class InstrumentedTestEnvironmentProvider(
    private val arguments: Bundle
) : BaseTestEnvironmentProvider() {
    override fun getEnvironmentValue(key: String): String? = arguments.getString(key)
}
