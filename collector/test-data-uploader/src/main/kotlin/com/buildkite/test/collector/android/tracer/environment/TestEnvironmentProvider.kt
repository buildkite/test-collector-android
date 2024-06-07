package com.buildkite.test.collector.android.tracer.environment

import com.buildkite.test.collector.android.model.RunEnvironment
import com.buildkite.test.collector.android.util.CollectorUtils.generateUUIDString

/**
 * Provides methods to access environment values for test analytics, including CI environment details and local configuration settings.
 */
interface TestEnvironmentProvider {
    /**
     * The API token for the test suite, used for authenticating requests.
     */
    val testSuiteApiToken: String?

    /**
     * Indicates whether debug logging is enabled.
     */
    val isDebugEnabled: Boolean

    /**
     * Retrieves the runtime environment details, such as CI system information or local development defaults.
     *
     * @param defaultKey A default key to use if no CI environment is detected.
     * @return A [RunEnvironment] instance containing the relevant environment details.
     */
    fun getRunEnvironment(defaultKey: String = generateUUIDString()): RunEnvironment
}
