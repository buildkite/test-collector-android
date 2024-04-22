package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.model.TestDetails
import com.buildkite.test.collector.android.model.TestFailureExpanded
import com.buildkite.test.collector.android.model.TestHistory
import com.buildkite.test.collector.android.tracer.TestObserver
import com.buildkite.test.collector.android.tracer.environment.configureUnitTestUploader
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestResult

/**
 * A Gradle plugin that automates the collection and uploading of unit test results to Buildkite's Test Analytics service.
 * It attaches a listener [Test] task within Gradle projects, capturing real-time test events and outcomes.
 * This enables detailed monitoring and systematic reporting of test results, which are uploaded directly to the analytics portal
 * at the conclusion of test suite.
 */
class UnitTestCollectorPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.withType(Test::class.java) { test ->

            test.addTestListener(object : TestListener {
                private val testUploader = configureUnitTestUploader()
                private val testObserver = TestObserver()
                private val testCollection: MutableList<TestDetails> = mutableListOf()

                override fun beforeSuite(suite: TestDescriptor) {
                    /* Nothing to do before the test suite has started */
                }

                override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                    if (isFinalTestSuiteCall(testDescription = suite)) {
                        testUploader.configureUploadData(testCollection = testCollection)
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
                            // Handle the case where [TestResult] is unexpectedly null
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
            })
        }
    }
}
