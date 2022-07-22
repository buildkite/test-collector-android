package com.buildkite.instrumented_test_collector

import com.buildkite.test_data_uploader.models.FailureExpanded
import com.buildkite.test_data_uploader.models.Span
import com.buildkite.test_data_uploader.models.TestDetails
import com.buildkite.test_data_uploader.models.TraceResult
import com.buildkite.test_data_uploader.test.TestObserver
import com.buildkite.test_data_uploader.test.environment.configureInstrumentedTestUploader
import org.junit.runner.Description
import org.junit.runner.notification.Failure
import org.junit.runner.notification.RunListener

abstract class InstrumentedTestCollector(
    apiToken: String,
    isDebugEnabled: Boolean = false
) : RunListener() {
    private val testObserver = TestObserver()
    private val testUploader = configureInstrumentedTestUploader(apiToken, isDebugEnabled)

    override fun testSuiteStarted(testDescription: Description?) {}

    override fun testSuiteFinished(description: Description?) {
        description?.let { testSuite ->
            if ((testSuite.displayName.isNullOrEmpty() || testSuite.displayName == "null") && (testSuite.className.isNullOrEmpty() || testSuite.className == "null")) {
                testUploader.configureUploadData(testCollection = testObserver.collection)
            }
        }
    }

    override fun testStarted(testDescription: Description?) {
        testObserver.recordStartTime()
    }

    override fun testFinished(testDescription: Description?) {
        testObserver.recordEndTime()

        if (testObserver.result != TraceResult.Failed) {
            testObserver.result = TraceResult.Passed
        }

        testDescription?.let { test ->
            addTestDetailsToCollection(test = test)
        }
    }

    override fun testFailure(failureDetails: Failure?) {
        failureDetails?.let { failure ->
            testObserver.result = TraceResult.Failed
            testObserver.failureReason = failure.exception.toString()
            testObserver.failureExpanded = listOf(
                FailureExpanded(
                    expanded = failure.trimmedTrace.split("\n").map { it.trim() },
                    backtrace = failure.trace.split("\n").map { it.trim() },
                )
            )
        }
    }

    override fun testIgnored(testDescription: Description?) {
        testObserver.result = TraceResult.Skipped

        testDescription?.let { test ->
            addTestDetailsToCollection(test = test)
        }
    }

    private fun addTestDetailsToCollection(test: Description) {
        val testSpan = Span(
            startAt = testObserver.startTime,
            endAt = testObserver.endTime,
            duration = testObserver.calculateSpanDuration(),
        )

        val testDetails = TestDetails(
            scope = test.testClass.name,
            name = test.methodName,
            identifier = "${test.className}.${test.methodName}",
            location = test.className,
            fileName = null,
            result = testObserver.result,
            failureReason = testObserver.failureReason,
            failureExpanded = testObserver.failureExpanded,
            history = testSpan
        )

        testObserver.collection.add(testDetails)
        testObserver.resetTestData()
    }
}
