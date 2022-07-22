package com.buildkite.test_data_uploader.models

import com.google.gson.annotations.SerializedName

enum class TraceResult {
    @SerializedName("passed") Passed,
    @SerializedName("failed") Failed,
    @SerializedName("skipped") Skipped,
    @SerializedName("unknown") Unknown
}
