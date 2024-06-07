package com.buildkite.test.collector.android

import androidx.test.platform.app.InstrumentationRegistry
import com.buildkite.test.collector.android.environment.InstrumentedTestEnvironmentProvider
import com.buildkite.test.collector.android.tracer.BuildkiteTestObserver
import com.buildkite.test.collector.android.util.logger.LoggerFactory
import org.junit.runner.Description
import org.junit.runner.notification.Failure
import org.junit.runner.notification.RunListener

/**
 * Collects and uploads instrumented test results to Buildkite's Test Analytics service.
 * This class extends JUnit's [RunListener] to capture real-time events during the execution of instrumented tests,
 * enabling precise monitoring and reporting of test outcomes. It delegates actual event handling to [InstrumentedTestListener].
 */
class InstrumentedTestCollector : RunListener() {
    private val listener: InstrumentedTestListener? by lazy { configureTestListener() }

    private fun configureTestListener(): InstrumentedTestListener? {
        val arguments = InstrumentationRegistry.getArguments()
        val testEnvironmentProvider = InstrumentedTestEnvironmentProvider(arguments = arguments)
        val testSuiteApiToken = testEnvironmentProvider.testSuiteApiToken
        val logger = LoggerFactory.create(
            isDebugEnabled = testEnvironmentProvider.isDebugEnabled,
            tag = "Buildkite-InstrumentedTestCollector"
        )

        if (testSuiteApiToken == null) {
            logger.error {
                "Missing or invalid 'BUILDKITE_ANALYTICS_TOKEN'. " +
                    "Instrumented test analytics data will not be collected and uploaded. Please set the environment variable to enable analytics."
            }
            return null
        }

        return InstrumentedTestListener(
            testUploader = BuildKiteTestDataUploader(
                testSuiteApiToken = testSuiteApiToken,
                runEnvironment = testEnvironmentProvider.getRunEnvironment(),
                logger = logger
            ),
            testObserver = BuildkiteTestObserver()
        )
    }

    override fun testSuiteStarted(description: Description) {
        listener?.testSuiteStarted(description)
    }

    override fun testSuiteFinished(description: Description) {
        listener?.testSuiteFinished(description)
    }

    override fun testStarted(description: Description) {
        listener?.testStarted(description)
    }

    override fun testFinished(description: Description) {
        listener?.testFinished(description)
    }

    override fun testFailure(failure: Failure) {
        listener?.testFailure(failure)
    }

    override fun testIgnored(description: Description) {
        listener?.testIgnored(description)
    }
}
