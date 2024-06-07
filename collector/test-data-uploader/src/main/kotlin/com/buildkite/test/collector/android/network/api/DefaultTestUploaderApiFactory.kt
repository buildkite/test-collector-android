package com.buildkite.test.collector.android.network.api

import com.buildkite.test.collector.android.network.TestAnalyticsRetrofit

/**
 * Default implementation of [TestUploaderApiFactory] that creates instance using Retrofit.
 */
internal class DefaultTestUploaderApiFactory : TestUploaderApiFactory {
    override fun create(testSuiteApiToken: String): TestUploaderApi {
        return TestAnalyticsRetrofit.getRetrofitInstance(testSuiteApiToken = testSuiteApiToken)
            .create(TestUploaderApi::class.java)
    }
}
