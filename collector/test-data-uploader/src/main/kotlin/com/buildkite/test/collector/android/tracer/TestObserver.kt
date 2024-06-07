package com.buildkite.test.collector.android.tracer

import com.buildkite.test.collector.android.model.TestFailureExpanded
import com.buildkite.test.collector.android.model.TestOutcome

/**
 * Observes and records details for an individual test, including timing, outcome, and failure details.
 * The start and end times are recorded in nanoseconds.
 */
interface TestObserver {
    /**
     * The start time of the test in nanoseconds.
     */
    val startTime: Long

    /**
     * The end time of the test in nanoseconds.
     */
    val endTime: Long

    /**
     * The outcome of the test.
     */
    val outcome: TestOutcome

    /**
     * The reason for test failure, if any.
     */
    val failureReason: String?

    /**
     * Detailed information about test failures.
     */
    val failureDetails: List<TestFailureExpanded>?

    /**
     * Records the start time of a test in nanoseconds.
     */
    fun startTest()

    /**
     * Records the end time of a test in nanoseconds.
     */
    fun endTest()

    /**
     * Returns the duration of the test in seconds, ensuring a non-negative result.
     * The duration is calculated from the recorded start and end times.
     */
    fun getDuration(): Double

    /**
     * Marks the test as successfully passed.
     */
    fun recordSuccess()

    /**
     * Records a test failure with a reason and detailed explanation.
     *
     * @param reason The reason for the test failure.
     * @param details Detailed information about the failure.
     */
    fun recordFailure(reason: String, details: List<TestFailureExpanded> = emptyList())

    /**
     * Marks the test as skipped.
     */
    fun recordSkipped()

    /**
     * Resets all test data to initial state.
     */
    fun reset()
}
