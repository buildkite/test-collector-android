package com.buildkite.test.collector.android.util

internal fun String?.takeIfValid() =
    takeIf { value -> !value.isNullOrBlank() && value != "null" }
