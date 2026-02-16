package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.model.TestDetails
import com.buildkite.test.collector.android.model.TestFailureExpanded
import com.buildkite.test.collector.android.model.TestHistory
import com.buildkite.test.collector.android.tracer.TestObserver
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestResult

/**
 * A listener for Gradle's [Test] tasks that collects and uploads test results.
 * This listener observes the test execution, records details about each test, and uploads the results at the end of the test suite.
 *
 * @property testUploader The uploader used to send test data to the analytics service.
 * @property testObserver The observer used to track and record test details.
 */
internal class UnitTestListener(
    private val testUploader: TestDataUploader,
    private val testObserver: TestObserver
) : TestListener {
    private val testCollection: MutableList<TestDetails> = mutableListOf()

    override fun beforeSuite(suite: TestDescriptor) {
        /* Nothing to do before the test suite has started */
    }

    override fun afterSuite(suite: TestDescriptor, result: TestResult) {
        if (isFinalTestSuiteCall(testDescription = suite)) {
            testUploader.uploadTestData(testCollection = testCollection)
        }
    }

    override fun beforeTest(testDescriptor: TestDescriptor) {
        testObserver.startTest()
    }

    override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
        testObserver.endTest()

        when (result.resultType) {
            TestResult.ResultType.SUCCESS -> {
                testObserver.recordSuccess()
            }

            TestResult.ResultType.FAILURE -> {
                val failureReason = result.exception.toString()
                val failureDetails = result.exceptions.map { exception ->
                    TestFailureExpanded(
                        expanded = listOf("${exception.message}:${exception.cause}"),
                        backtrace = exception.stackTraceToString().split("\n")
                            .map { it.trim() }
                    )
                }
                testObserver.recordFailure(
                    reason = failureReason,
                    details = failureDetails
                )
            }

            TestResult.ResultType.SKIPPED -> {
                testObserver.recordSkipped()
            }

            null -> {
                // Handle the case where TestResult is unexpectedly null */
                testObserver.recordFailure(
                    reason = "TestResult type was unexpectedly null, indicating an error in the test framework."
                )
            }
        }

        addTestDetailsToCollection(test = testDescriptor)
    }

    private fun addTestDetailsToCollection(test: TestDescriptor) {
        val testHistory = TestHistory(
            startAt = testObserver.startTime,
            endAt = testObserver.endTime,
            duration = testObserver.getDuration()
        )

        val testDetails = TestDetails(
            scope = test.className,
            name = test.displayName,
            location = test.className,
            fileName = null,
            result = testObserver.outcome,
            failureReason = testObserver.failureReason,
            failureExpanded = testObserver.failureDetails,
            tags = testObserver.executionTags.ifEmpty { null },
            history = testHistory
        )

        testCollection.add(testDetails)
        testObserver.reset()
    }

    /**
     * Determines if the provided test suite descriptor indicates the final call of the test suite,
     * which is true when both className and parent are null.
     *
     * @param testDescription The test description. [TestDescriptor] can be atomic (a single test) or compound (containing children tests).
     */
    private fun isFinalTestSuiteCall(testDescription: TestDescriptor) =
        testDescription.className == null && testDescription.parent == null
}
