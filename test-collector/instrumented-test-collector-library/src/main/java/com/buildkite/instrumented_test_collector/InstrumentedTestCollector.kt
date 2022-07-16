package com.buildkite.instrumented_test_collector

import com.buildkite.test_data_uploader.TestDataUploader
import com.buildkite.test_data_uploader.domain.model.api.FailureExpanded
import com.buildkite.test_data_uploader.domain.model.api.Span
import com.buildkite.test_data_uploader.domain.model.api.SpanDuration
import com.buildkite.test_data_uploader.domain.model.api.SpanSection
import com.buildkite.test_data_uploader.domain.model.api.TestDetails
import com.buildkite.test_data_uploader.domain.model.api.TraceResult
import org.junit.runner.Description
import org.junit.runner.notification.Failure
import org.junit.runner.notification.RunListener

abstract class InstrumentedTestCollector(
    apiToken: String,
    isDebugEnabled: Boolean = false
) : RunListener() {

    private var testStartTime: Long = 0

    private var traceResult: TraceResult = TraceResult.Unknown
    private var testFailureReason: String = ""
    private var testFailureExpanded: List<FailureExpanded> = emptyList()

    private val testDataUploader =
        TestDataUploader(testSuiteApiToken = apiToken, isDebugEnabled = isDebugEnabled)
    private val testBatch: MutableList<TestDetails> = mutableListOf()

    override fun testSuiteStarted(testDescription: Description?) {
        /* Do Nothing */
    }

    override fun testSuiteFinished(description: Description?) {
        description?.let { testSuite ->
            if ((testSuite.displayName.isNullOrEmpty() || testSuite.displayName == "null") && (testSuite.className.isNullOrEmpty() || testSuite.className == "null")) {
                testDataUploader.collectTestBatch(testBatch = testBatch)
            }
        }
    }

    override fun testStarted(testDescription: Description?) {
        testStartTime = System.nanoTime()
    }

    override fun testFinished(testDescription: Description?) {
        val (startAt, endAt, duration) = SpanDuration(startAt = testStartTime).getSpanDuration()

        if (traceResult != TraceResult.Failed) {
            traceResult = TraceResult.Passed
        }

        val testSpan = Span(
            section = SpanSection.Top,
            startAt = startAt,
            endAt = endAt,
            duration = duration,
            detail = emptyMap()
        )

        testDescription?.let { description ->
            addTestToBatch(description = description, testSpan = testSpan)
        }
    }

    override fun testFailure(failureDetails: Failure?) {
        failureDetails?.let { failure ->
            traceResult = TraceResult.Failed
            testFailureReason = failure.exception.toString()
            testFailureExpanded = listOf(
                FailureExpanded(
                    expanded = failure.trimmedTrace.split("\n").map { it.trim() },
                    backtrace = failure.trace.split("\n").map { it.trim() },
                )
            )
        }
    }

    override fun testIgnored(testDescription: Description?) {
        traceResult = TraceResult.Skipped

        val testSpan = Span(
            section = SpanSection.Top,
            startAt = 0,
            endAt = 0,
            duration = 0.0,
            detail = emptyMap()
        )

        testDescription?.let { description ->
            addTestToBatch(description = description, testSpan = testSpan)
        }
    }

    private fun addTestToBatch(
        description: Description,
        testSpan: Span
    ) {
        val testDetails = TestDetails(
            scope = description.testClass.simpleName,
            name = description.methodName,
            identifier = "${description.className}.${description.methodName}",
            location = description.displayName,
            fileName = null,
            result = traceResult,
            failureReason = testFailureReason,
            failureExpanded = testFailureExpanded,
            history = testSpan
        )

        testBatch.add(testDetails)

        resetTestData()
    }

    private fun resetTestData() {
        traceResult = TraceResult.Unknown
        testFailureReason = ""
        testFailureExpanded = emptyList()
    }
}
