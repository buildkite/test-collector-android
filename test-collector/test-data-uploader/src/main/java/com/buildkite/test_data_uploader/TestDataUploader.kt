package com.buildkite.test_data_uploader

import com.buildkite.test_data_uploader.data.network.RetroInstance
import com.buildkite.test_data_uploader.data.network.TestAnalyticsApi
import com.buildkite.test_data_uploader.domain.model.api.RunEnvironment
import com.buildkite.test_data_uploader.domain.model.api.TestData
import com.buildkite.test_data_uploader.domain.model.api.TestDetails

class TestDataUploader(
    val testSuiteApiToken: String
) {

    var loggingEnabled: Boolean = false

    fun collectTestBatch(testBatch: List<TestDetails>) {
        val runEnvironment = RunEnvironment().getEnvironmentValues()

        val testData = TestData(
            format = "json",
            runEnv = runEnvironment,
            data = testBatch
        )

        uploadTestData(testData = testData)
    }

    private fun uploadTestData(testData: TestData) {
        val retroService = RetroInstance.getRetroInstance(testSuiteApiToken = testSuiteApiToken)
            .create(TestAnalyticsApi::class.java)
        val uploadTestDataApiCall = retroService.uploadTestData(testData = testData)

        val executeApiCall = uploadTestDataApiCall.execute()

        if (loggingEnabled) {
            println("API response: ${executeApiCall.raw()}")
        }
    }
}
