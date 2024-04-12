package com.buildkite.test.collector.android.model

import com.google.gson.annotations.SerializedName

/**
 * Represents the outcome of the test.
 * */
enum class TestOutcome {
    @SerializedName("passed")
    Passed,

    @SerializedName("failed")
    Failed,

    @SerializedName("skipped")
    Skipped,

    @SerializedName("unknown")
    Unknown
}
