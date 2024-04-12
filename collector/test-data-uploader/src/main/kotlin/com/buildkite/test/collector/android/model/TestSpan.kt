package com.buildkite.test.collector.android.model

import com.google.gson.annotations.SerializedName

/**
 * Represents a specific operation or activity segment within a test, detailing its timing and additional information.
 *
 * This class captures the finer resolution of a test's execution duration, such as the time taken by an individual database query.
 *
 * @property section The category of this span, indicating the type of operation (e.g., SQL query, HTTP request). See [TestSpanSection].
 * @property startAt The start timestamp of the test span.
 * @property endAt The end timestamp of the test span.
 * @property duration The total duration of the test span.
 * @property detail [TestSpanDetail] object providing additional information about the span, required for certain section types like `http` or `sql`.
 */
data class TestSpan(
    @SerializedName("section") val section: TestSpanSection = TestSpanSection.Top,
    @SerializedName("start_at") val startAt: Long?,
    @SerializedName("end_at") val endAt: Long?,
    @SerializedName("duration") val duration: Double,
    @SerializedName("detail") val detail: TestSpanDetail? = null
)
