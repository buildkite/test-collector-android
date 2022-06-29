package com.buildkite.test_data_uploader.domain.model.api

import com.google.gson.annotations.SerializedName

data class Span(
    val section: SpanSection = SpanSection.Top,
    @SerializedName(value = "start_at")
    val startAt: Long?,
    @SerializedName(value = "end_at")
    val endAt: Long?,
    val duration: Double,
    val detail: Map<String, String>? = emptyMap(),
    val children: List<Span> = emptyList(),
)
