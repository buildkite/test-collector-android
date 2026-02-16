package com.buildkite.test.collector.android.tracer

import com.buildkite.test.collector.android.model.TestFailureExpanded
import com.buildkite.test.collector.android.model.TestOutcome
import kotlin.math.max

/**
 * Default implementation of [TestObserver] that observes and records details for an individual test,
 * including timing, outcome, and failure details.
 */
class BuildkiteTestObserver : TestObserver {
    override var startTime: Long = 0
        private set
    override var endTime: Long = 0
        private set
    override var outcome: TestOutcome = TestOutcome.Unknown
        private set
    override var failureReason: String? = null
        private set
    override var failureDetails: List<TestFailureExpanded>? = null
        private set

    private val _executionTags: MutableMap<String, String> = linkedMapOf()
    override val executionTags: Map<String, String> get() = _executionTags

    override fun setExecutionTag(key: String, value: String) {
        _executionTags[key] = value
    }

    override fun startTest() {
        startTime = System.nanoTime()
    }

    override fun endTest() {
        endTime = System.nanoTime()
    }

    override fun getDuration(): Double = max(0.0, (endTime - startTime) / 1_000_000_000.0)

    override fun recordSuccess() {
        outcome = TestOutcome.Passed
    }

    override fun recordFailure(
        reason: String,
        details: List<TestFailureExpanded>
    ) {
        outcome = TestOutcome.Failed
        failureReason = reason
        failureDetails = details
    }

    override fun recordSkipped() {
        outcome = TestOutcome.Skipped
    }

    override fun reset() {
        startTime = 0
        endTime = 0
        outcome = TestOutcome.Unknown
        failureReason = null
        failureDetails = null
        _executionTags.clear()
    }
}
