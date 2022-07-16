package com.buildkite.unit_test_collector_plugin

import com.buildkite.test_data_uploader.TestDataUploader
import com.buildkite.test_data_uploader.domain.model.api.FailureExpanded
import com.buildkite.test_data_uploader.domain.model.api.Span
import com.buildkite.test_data_uploader.domain.model.api.SpanDuration
import com.buildkite.test_data_uploader.domain.model.api.SpanSection
import com.buildkite.test_data_uploader.domain.model.api.TestDetails
import com.buildkite.test_data_uploader.domain.model.api.TraceResult
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestResult

class UnitTestCollectorPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val testDataUploader = TestDataUploader(
            testSuiteApiToken = System.getenv("BUILDKITE_ANALYTICS_TOKEN"),
            isDebugEnabled = System.getenv("BUILDKITE_ANALYTICS_DEBUG_ENABLED")
                ?.let { it.toBoolean() } ?: false
        )

        project.tasks.withType(Test::class.java) { test ->
            test.addTestListener(object : TestListener {
                private var testStartTime: Long = 0

                private var traceResult: TraceResult = TraceResult.Unknown
                private var testFailureReason: String = ""
                private var testFailureExpanded: List<FailureExpanded> = emptyList()

                private val testBatch: MutableList<TestDetails> = mutableListOf()

                override fun beforeSuite(suite: TestDescriptor?) {
                    /* Do Nothing */
                }

                override fun afterSuite(suite: TestDescriptor?, result: TestResult?) {
                    suite?.let { testSuite ->
                        if (testSuite.className == null && testSuite.parent == null) {
                            testDataUploader.collectTestBatch(testBatch = testBatch)
                        }
                    }
                }

                override fun beforeTest(testDescriptor: TestDescriptor?) {
                    testStartTime = System.nanoTime()
                }

                override fun afterTest(testDescriptor: TestDescriptor?, result: TestResult?) {
                    val (startAt, endAt, duration) = SpanDuration(startAt = testStartTime).getSpanDuration()

                    when (result?.resultType) {
                        TestResult.ResultType.SUCCESS -> {
                            traceResult = TraceResult.Passed
                        }
                        TestResult.ResultType.FAILURE -> {
                            traceResult = TraceResult.Failed
                            testFailureReason = result.exception.toString()
                            testFailureExpanded = result.exceptions.map { exception ->
                                FailureExpanded(
                                    expanded = listOf("${exception.message}:${exception.cause}"),
                                    backtrace = exception.stackTraceToString().split("\n")
                                        .map { it.trim() }
                                )
                            }
                        }
                        TestResult.ResultType.SKIPPED -> {
                            traceResult = TraceResult.Skipped
                        }
                        null -> {
                            traceResult = TraceResult.Passed
                        }
                    }

                    val testSpan = Span(
                        section = SpanSection.Top,
                        startAt = startAt,
                        endAt = endAt,
                        duration = duration,
                        detail = emptyMap()
                    )

                    testDescriptor?.let { descriptor ->
                        addTestToBatch(descriptor = descriptor, testSpan = testSpan)
                    }
                }

                private fun addTestToBatch(
                    descriptor: TestDescriptor,
                    testSpan: Span
                ) {
                    val testDetails = TestDetails(
                        scope = descriptor.className,
                        name = descriptor.displayName,
                        identifier = "${descriptor.className}.${descriptor.displayName}",
                        location = descriptor.className,
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
            })
        }
    }
}
