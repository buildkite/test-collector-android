package com.buildkite.test_data_uploader.domain.model.api

import com.google.gson.annotations.SerializedName

data class TestData(
    @SerializedName("format") val format: String = "json",
    @SerializedName("run_env") val runEnv: RunEnvironment,
    @SerializedName("data") val data: List<TestDetails>
)
