package com.buildkite.test.collector.android.models

import com.buildkite.test.collector.android.util.Helpers.generateUUID
import com.google.gson.annotations.SerializedName

data class TestDetails(
    @SerializedName("id") val id: String = generateUUID(),
    @SerializedName("scope") val scope: String?,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String?,
    @SerializedName("file_name") val fileName: String?,
    @SerializedName("result") val result: TraceResult,
    @SerializedName("failure_reason") val failureReason: String?,
    @SerializedName("failure_expanded") val failureExpanded: List<FailureExpanded> = emptyList(),
    @SerializedName("history") val history: Span
)
