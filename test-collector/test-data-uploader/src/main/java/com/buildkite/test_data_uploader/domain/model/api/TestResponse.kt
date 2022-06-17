package com.buildkite.test_data_uploader.domain.model.api

import com.google.gson.annotations.SerializedName

data class TestResponse(
    val id: String,
    @SerializedName(value = "run_id")
    val runId: String,
    val queued: Int,
    val skipped: Int,
    val errors: List<String>,
    @SerializedName(value = "run_url")
    val runUrl: String,
)
