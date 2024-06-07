package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.model.TestDetails
import com.buildkite.test.collector.android.model.TestFailureExpanded
import com.buildkite.test.collector.android.model.TestHistory
import com.buildkite.test.collector.android.model.TestOutcome
import com.buildkite.test.collector.android.tracer.TestObserver
import org.junit.runner.Description
import org.junit.runner.notification.Failure
import org.junit.runner.notification.RunListener

/**
 * Listens to instrumented test events and collects test details.
 * This class extends JUnit's [RunListener] to capture real-time events during the execution of tests.
 * It automatically gathers detailed test results and uploads them directly to the analytics portal at the conclusion of the test suite.
 *
 * @property testUploader The uploader used to send test data to the analytics service.
 * @property testObserver The observer used to track and record test details.
 */
internal class InstrumentedTestListener(
    private val testUploader: TestDataUploader,
    private val testObserver: TestObserver
) : RunListener() {
    private val testCollection: MutableList<TestDetails> = mutableListOf()

    override fun testSuiteStarted(testDescription: Description) {
        /* Nothing to do before the test suite has started */
    }

    override fun testSuiteFinished(description: Description) {
        if (isFinalTestSuiteCall(testDescription = description)) {
            testUploader.uploadTestData(testCollection = testCollection)
        }
    }

    override fun testStarted(testDescription: Description) {
        testObserver.startTest()
    }

    override fun testFinished(testDescription: Description) {
        testObserver.endTest()

        if (testObserver.outcome != TestOutcome.Failed) {
            testObserver.recordSuccess()
        }

        addTestDetailsToCollection(test = testDescription)
    }

    override fun testFailure(failureDetails: Failure) {
        val failureReason = failureDetails.exception.toString()
        val details = listOf(
            TestFailureExpanded(
                expanded = failureDetails.trimmedTrace.split("\n").map { it.trim() },
                backtrace = failureDetails.trace.split("\n").map { it.trim() },
            )
        )
        testObserver.recordFailure(reason = failureReason, details = details)
    }

    override fun testIgnored(testDescription: Description) {
        testObserver.recordSkipped()

        addTestDetailsToCollection(test = testDescription)
    }

    private fun addTestDetailsToCollection(test: Description) {
        val testHistory = TestHistory(
            startAt = testObserver.startTime,
            endAt = testObserver.endTime,
            duration = testObserver.getDuration()
        )

        val testDetails = TestDetails(
            scope = test.testClass.name,
            name = test.methodName,
            location = test.className,
            fileName = null,
            result = testObserver.outcome,
            failureReason = testObserver.failureReason,
            failureExpanded = testObserver.failureDetails,
            history = testHistory
        )

        testCollection.add(testDetails)
        testObserver.reset()
    }

    /**
     * Determines if the provided test suite descriptor indicates the final call of the test suite,
     * which is true when both displayName and className are null.
     *
     * @param testDescription The test description. [Description] can be atomic (a single test) or compound (containing children tests).
     */
    private fun isFinalTestSuiteCall(testDescription: Description) =
        (testDescription.displayName.isNullOrEmpty() || testDescription.displayName == "null") && (testDescription.className.isNullOrEmpty() || testDescription.className == "null")
}
