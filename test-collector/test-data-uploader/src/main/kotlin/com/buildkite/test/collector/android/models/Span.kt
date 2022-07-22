package com.buildkite.test.collector.android.models

import com.google.gson.annotations.SerializedName

data class Span(
    @SerializedName("section") val section: SpanSection = SpanSection.Top,
    @SerializedName("start_at") val startAt: Long?,
    @SerializedName("end_at") val endAt: Long?,
    @SerializedName("duration") val duration: Double,
    @SerializedName("detail") val detail: Map<String, String>? = emptyMap(),
    @SerializedName("children") val children: List<Span> = emptyList(),
)
