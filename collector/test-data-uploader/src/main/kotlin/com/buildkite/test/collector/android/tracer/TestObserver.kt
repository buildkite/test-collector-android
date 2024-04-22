package com.buildkite.test.collector.android.tracer

import com.buildkite.test.collector.android.model.TestFailureExpanded
import com.buildkite.test.collector.android.model.TestOutcome
import kotlin.math.max

/**
 * Observes and records details for an individual test, including timing, outcome, and failure details.
 */
class TestObserver {
    var startTime: Long = 0
        private set
    var endTime: Long = 0
        private set
    var outcome: TestOutcome = TestOutcome.Unknown
        private set
    var failureReason: String? = null
        private set
    var failureDetails: List<TestFailureExpanded>? = null
        private set

    /**
     * Records the start time of a test in nanoseconds.
     */
    fun startTest() {
        startTime = System.nanoTime()
    }

    /**
     * Records the end time of a test in nanoseconds.
     */
    fun endTest() {
        endTime = System.nanoTime()
    }

    /**
     * Returns the duration of the test in seconds, ensuring a non-negative result.
     */
    fun getDuration(): Double = max(0.0, (endTime - startTime) / 1_000_000_000.0)

    /**
     * Marks the test as successfully passed.
     */
    fun recordSuccess() {
        outcome = TestOutcome.Passed
    }

    /**
     * Records a test failure with a reason and detailed explanation.
     */
    fun recordFailure(
        reason: String,
        details: List<TestFailureExpanded> = emptyList()
    ) {
        outcome = TestOutcome.Failed
        failureReason = reason
        failureDetails = details
    }

    /**
     * Marks the test as skipped.
     */
    fun recordSkipped() {
        outcome = TestOutcome.Skipped
    }

    /**
     * Resets all test data to initial state.
     */
    fun reset() {
        startTime = 0
        endTime = 0
        outcome = TestOutcome.Unknown
        failureReason = null
        failureDetails = null
    }
}
