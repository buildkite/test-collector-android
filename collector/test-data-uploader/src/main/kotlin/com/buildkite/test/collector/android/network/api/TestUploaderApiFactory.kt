package com.buildkite.test.collector.android.network.api

/**
 * Factory interface for creating instances of [TestUploaderApi].
 */
internal interface TestUploaderApiFactory {
    /**
     * Creates an instance of [TestUploaderApi] using the provided test suite API token.
     *
     * @param testSuiteApiToken The API token to authenticate the requests.
     * @return An instance of [TestUploaderApi].
     */
    fun create(testSuiteApiToken: String): TestUploaderApi
}
