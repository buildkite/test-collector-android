package com.buildkite.test.collector.android

import androidx.test.platform.app.InstrumentationRegistry
import com.buildkite.test.collector.android.environment.InstrumentedTestEnvironmentProvider
import com.buildkite.test.collector.android.tracer.BuildkiteTestObserver
import org.junit.runner.Description
import org.junit.runner.notification.Failure
import org.junit.runner.notification.RunListener

/**
 * Collects and uploads instrumented test results to Buildkite's Test Analytics service.
 * This class extends JUnit's [RunListener] to capture real-time events during the execution of instrumented tests,
 * enabling precise monitoring and reporting of test outcomes. It delegates actual event handling to [InstrumentedTestListener].
 */
class InstrumentedTestCollector : RunListener() {
    private val listener: InstrumentedTestListener by lazy { configureTestListener() }

    private fun configureTestListener(): InstrumentedTestListener {
        val arguments = InstrumentationRegistry.getArguments()
        val environmentProvider = InstrumentedTestEnvironmentProvider(arguments = arguments)

        return InstrumentedTestListener(
            testUploader = BuildKiteTestDataUploader(testEnvironmentProvider = environmentProvider),
            testObserver = BuildkiteTestObserver()
        )
    }

    override fun testSuiteStarted(description: Description) {
        listener.testSuiteStarted(description)
    }

    override fun testSuiteFinished(description: Description) {
        listener.testSuiteFinished(description)
    }

    override fun testStarted(description: Description) {
        listener.testStarted(description)
    }

    override fun testFinished(description: Description) {
        listener.testFinished(description)
    }

    override fun testFailure(failure: Failure) {
        listener.testFailure(failure)
    }

    override fun testIgnored(description: Description) {
        listener.testIgnored(description)
    }
}
