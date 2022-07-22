package com.buildkite.sample

import com.buildkite.test.collector.android.InstrumentedTestCollector

class MyTestCollector : InstrumentedTestCollector(
    apiToken = BuildConfig.BUILDKITE_ANALYTICS_TOKEN,
    isDebugEnabled = BuildConfig.BUILDKITE_ANALYTICS_DEBUG_ENABLED
)
