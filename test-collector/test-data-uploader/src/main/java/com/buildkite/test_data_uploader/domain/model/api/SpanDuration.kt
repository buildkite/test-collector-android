package com.buildkite.test_data_uploader.domain.model.api

data class SpanDuration(
    val startAt: Long,
    val endAt: Long = System.nanoTime(),
) {
    fun getSpanDuration(): Triple<Long, Long, Double> {
        val duration = endAt.minus(startAt).toDouble() / 1000000000

        return Triple(startAt, endAt, duration)
    }
}
