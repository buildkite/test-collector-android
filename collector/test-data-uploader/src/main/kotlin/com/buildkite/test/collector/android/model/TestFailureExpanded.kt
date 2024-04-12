package com.buildkite.test.collector.android.model

import com.google.gson.annotations.SerializedName

/**
 * Represents additional details about a test failure.
 *
 * @property backtrace A list of strings, each representing a frame in the call stack at the failure point.
 * @property expanded A list of strings detailing the failure, including error messages and any supplementary context.
 */
data class TestFailureExpanded(
    @SerializedName("backtrace") val backtrace: List<String> = emptyList(),
    @SerializedName("expanded") val expanded: List<String> = emptyList()
)
