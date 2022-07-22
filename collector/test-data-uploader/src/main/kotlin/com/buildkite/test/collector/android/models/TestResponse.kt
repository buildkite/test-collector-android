package com.buildkite.test.collector.android.models

import com.google.gson.annotations.SerializedName

data class TestResponse(
    @SerializedName("id") val id: String,
    @SerializedName("run_id") val runId: String,
    @SerializedName("queued") val queued: Int,
    @SerializedName("skipped") val skipped: Int,
    @SerializedName("errors") val errors: List<String>,
    @SerializedName("run_url") val runUrl: String,
)
