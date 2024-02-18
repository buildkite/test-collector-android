package com.buildkite.test.collector.android.models

import com.google.gson.annotations.SerializedName

internal data class TestData(
    @SerializedName("format") val format: String = "json",
    @SerializedName("run_env") val runEnvironment: RunEnvironment,
    @SerializedName("data") val data: List<TestDetails>
)
