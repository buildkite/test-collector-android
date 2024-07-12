package com.buildkite.test.collector.android.environment

import com.buildkite.test.collector.android.tracer.environment.BaseTestEnvironmentProvider

/**
 * Provides environment values for unit tests by fetching from system properties.
 */
internal class UnitTestEnvironmentProvider : BaseTestEnvironmentProvider() {
    override fun getEnvironmentValue(key: String): String? = System.getenv(key)
}
