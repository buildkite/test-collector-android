package com.buildkite.test.collector.android.example

import com.buildkite.test.collector.android.InstrumentedTestCollector

class ExampleTestCollector : InstrumentedTestCollector(
    apiToken = BuildConfig.BUILDKITE_ANALYTICS_TOKEN,
    isDebugEnabled = BuildConfig.BUILDKITE_ANALYTICS_DEBUG_ENABLED
)
