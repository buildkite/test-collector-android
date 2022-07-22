package com.buildkite.test.collector.android.tracer

import com.buildkite.test.collector.android.models.FailureExpanded
import com.buildkite.test.collector.android.models.TestDetails
import com.buildkite.test.collector.android.models.TraceResult

data class TestObserver(
    var startTime: Long = 0,
    var endTime: Long = 0,
    var result: TraceResult = TraceResult.Unknown,
    var failureReason: String = "",
    var failureExpanded: List<FailureExpanded> = emptyList(),
    val collection: MutableList<TestDetails> = mutableListOf()
) {
    fun recordStartTime() {
        startTime = System.nanoTime()
    }

    fun recordEndTime() {
        endTime = System.nanoTime()
    }

    fun calculateSpanDuration(): Double {
        return endTime.minus(startTime).toDouble() / 1000000000
    }

    fun resetTestData() {
        startTime = 0
        endTime = 0
        result = TraceResult.Unknown
        failureReason = ""
        failureExpanded = emptyList()
    }
}
