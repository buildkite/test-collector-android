package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.models.FailureExpanded
import com.buildkite.test.collector.android.models.Span
import com.buildkite.test.collector.android.models.TestDetails
import com.buildkite.test.collector.android.models.TraceResult
import com.buildkite.test.collector.android.tracer.TestObserver
import com.buildkite.test.collector.android.tracer.environment.configureUnitTestUploader
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestResult

class UnitTestCollectorPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.withType(Test::class.java) { test ->

            test.addTestListener(object : TestListener {
                private val testObserver = TestObserver()
                private val testUploader = configureUnitTestUploader()

                override fun beforeSuite(suite: TestDescriptor?) {}

                override fun afterSuite(suite: TestDescriptor?, result: TestResult?) {
                    suite?.let { testSuite ->
                        if (testSuite.className == null && testSuite.parent == null) {
                            testUploader.configureUploadData(testCollection = testObserver.collection)
                        }
                    }
                }

                override fun beforeTest(testDescriptor: TestDescriptor?) {
                    testObserver.recordStartTime()
                }

                override fun afterTest(testDescriptor: TestDescriptor?, result: TestResult?) {
                    testObserver.recordEndTime()

                    when (result?.resultType) {
                        TestResult.ResultType.SUCCESS -> {
                            testObserver.result = TraceResult.Passed
                        }
                        TestResult.ResultType.FAILURE -> {
                            testObserver.result = TraceResult.Failed
                            testObserver.failureReason = result.exception.toString()
                            testObserver.failureExpanded = result.exceptions.map { exception ->
                                FailureExpanded(
                                    expanded = listOf("${exception.message}:${exception.cause}"),
                                    backtrace = exception.stackTraceToString().split("\n")
                                        .map { it.trim() }
                                )
                            }
                        }
                        TestResult.ResultType.SKIPPED -> {
                            testObserver.endTime = 0
                            testObserver.startTime = 0
                            testObserver.result = TraceResult.Skipped
                        }
                        null -> {
                            testObserver.result = TraceResult.Passed
                        }
                    }

                    testDescriptor?.let { test ->
                        addTestDetailsToCollection(test = test)
                    }
                }

                private fun addTestDetailsToCollection(test: TestDescriptor) {
                    val testSpan = Span(
                        startAt = testObserver.startTime,
                        endAt = testObserver.endTime,
                        duration = testObserver.calculateSpanDuration(),
                    )

                    val testDetails = TestDetails(
                        scope = test.className,
                        name = test.displayName,
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
            })
        }
    }
}
