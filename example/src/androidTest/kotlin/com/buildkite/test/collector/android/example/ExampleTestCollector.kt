package com.buildkite.test.collector.android.example

import com.buildkite.test.collector.android.InstrumentedTestCollector

/**
 * Configures `ExampleTestCollector` with Buildkite test collector configurations.
 * Values are derived from local/CI environment variables and set in [BuildConfig] at build time.
 */
class ExampleTestCollector : InstrumentedTestCollector(
    apiToken = BuildConfig.BUILDKITE_ANALYTICS_TOKEN,
    isDebugEnabled = BuildConfig.BUILDKITE_ANALYTICS_DEBUG_ENABLED
)
