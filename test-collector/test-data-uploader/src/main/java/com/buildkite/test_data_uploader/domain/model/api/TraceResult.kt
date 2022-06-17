package com.buildkite.test_data_uploader.domain.model.api

import com.google.gson.annotations.SerializedName

enum class TraceResult {
    @SerializedName(value = "passed") Passed,
    @SerializedName(value = "failed") Failed,
    @SerializedName(value = "skipped") Skipped,
    @SerializedName(value = "unknown") Unknown
}
