package com.buildkite.test_data_uploader.domain.model.api

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class TestDetails(
    @SerializedName("id") val id: String = generateUUID(),
    @SerializedName("scope") val scope: String?,
    @SerializedName("name") val name: String,
    @SerializedName("identifier") val identifier: String,
    @SerializedName("location") val location: String?,
    @SerializedName("file_name") val fileName: String?,
    @SerializedName("result") val result: TraceResult,
    @SerializedName("failure_reason") val failureReason: String?,
    @SerializedName("failure_expanded") val failureExpanded: List<FailureExpanded> = emptyList(),
    @SerializedName("history") val history: Span
)

private fun generateUUID(): String {
    return UUID.randomUUID().toString()
}
