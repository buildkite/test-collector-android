package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.environment.UnitTestEnvironmentProvider
import com.buildkite.test.collector.android.tracer.BuildkiteTestObserver
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
        project.tasks.withType(Test::class.java).configureEach { test ->
            val testListener = UnitTestListener(
                testUploader = BuildKiteTestDataUploader(testEnvironmentProvider = UnitTestEnvironmentProvider()),
                testObserver = BuildkiteTestObserver()
            )

            test.addTestListener(testListener)
        }
    }
}
