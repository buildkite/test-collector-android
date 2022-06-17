package com.buildkite.test_data_uploader.domain.model.api

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class TestDetails(
    @SerializedName(value = "id") val id: String = generateUUID(),
    @SerializedName(value = "scope") val scope: String?,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "identifier") val identifier: String,
    @SerializedName(value = "location") val location: String?,
    @SerializedName(value = "file_name") val fileName: String?,
    @SerializedName(value = "result") val result: TraceResult,
    @SerializedName(value = "failure_reason") val failureReason: String?,
    @SerializedName(value = "failure_expanded") val failureExpanded: List<FailureExpanded> = emptyList(),
    @SerializedName(value = "history") val history: Span
)

private fun generateUUID(): String {
    return UUID.randomUUID().toString()
}
