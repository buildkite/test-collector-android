package com.buildkite.test.collector.android.model

import com.google.gson.annotations.SerializedName

/**
 * Represents the overall duration and phases of an individual test.
 *
 * @property startAt The start timestamp of the test.
 * @property endAt The end timestamp of the test.
 * @property duration The total duration of the test.
 * @property children A collection of [TestSpan] objects detailing specific segments or operations within the test.
 */
data class TestHistory(
    @SerializedName("start_at") val startAt: Long,
    @SerializedName("end_at") val endAt: Long?,
    @SerializedName("duration") val duration: Double,
    @SerializedName("children") val children: List<TestSpan>? = null,
)
