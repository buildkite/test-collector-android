package com.buildkite.test_data_uploader.domain.model.api

import com.google.gson.annotations.SerializedName

data class TestData(
    val format: String = "json",
    @SerializedName(value = "run_env")
    val runEnv: RunEnvironment,
    val data: List<TestDetails>
)
