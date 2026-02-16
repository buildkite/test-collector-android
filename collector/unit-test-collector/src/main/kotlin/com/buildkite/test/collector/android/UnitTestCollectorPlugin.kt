package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.environment.UnitTestEnvironmentProvider
import com.buildkite.test.collector.android.tracer.BuildkiteTestObserver
import com.buildkite.test.collector.android.util.logger.LoggerFactory
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test

/**
 * A Gradle plugin that automates the collection and uploading of unit test results to Buildkite's Test Analytics service.
 * It attaches a [UnitTestListener] to the [Test] tasks within Gradle projects, capturing real-time test events and outcomes.
 * This enables detailed monitoring and systematic reporting of test results, which are uploaded directly to the analytics portal
 * at the conclusion of the test suite.
 */
class UnitTestCollectorPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.findByType(UnitTestCollectorExtension::class.java)
            ?: project.extensions.create("buildkiteTestAnalytics", UnitTestCollectorExtension::class.java)

        project.tasks.withType(Test::class.java).configureEach { test ->
            val testEnvironmentProvider = UnitTestEnvironmentProvider()
            val testSuiteApiToken = testEnvironmentProvider.testSuiteApiToken
            val logger = LoggerFactory.create(
                isDebugEnabled = testEnvironmentProvider.isDebugEnabled,
                tag = "Buildkite-UnitTestCollector"
            )

            if (testSuiteApiToken == null) {
                logger.error {
                    "Missing or invalid 'BUILDKITE_ANALYTICS_TOKEN'. " +
                        "Unit test analytics data will not be collected and uploaded. Please set the environment variable to enable analytics."
                }
                return@configureEach
            }

            val uploadTags = extension.tags + testEnvironmentProvider.uploadTags

            val testListener = UnitTestListener(
                testUploader = BuildKiteTestDataUploader(
                    testSuiteApiToken = testSuiteApiToken,
                    runEnvironment = testEnvironmentProvider.getRunEnvironment(),
                    uploadTags = uploadTags,
                    logger = logger
                ),
                testObserver = BuildkiteTestObserver()
            )

            test.addTestListener(testListener)
        }
    }
}
