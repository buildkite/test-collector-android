package com.buildkite.test_data_uploader

import com.buildkite.test_data_uploader.data.network.RetroInstance
import com.buildkite.test_data_uploader.data.network.TestAnalyticsApi
import com.buildkite.test_data_uploader.domain.model.api.RunEnvironment
import com.buildkite.test_data_uploader.domain.model.api.TestData
import com.buildkite.test_data_uploader.domain.model.api.TestDetails
import com.buildkite.test_data_uploader.domain.model.api.TestResponse
import retrofit2.Response

class TestDataUploader(
    val testSuiteApiToken: String,
    val isDebugEnabled: Boolean
) {
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

        if (isDebugEnabled) {
            printResponseLog(executeApiCall)
        }
    }

    private fun printResponseLog(executeApiCall: Response<TestResponse>) {
        when (val apiResponseCode = executeApiCall.raw().code) {
            202 -> println("\nTest analytics data successfully uploaded to the BuildKite Test Suite.")
            else -> println("\nError uploading test analytics data to the BuildKite Test Suite. Error code: $apiResponseCode")
        }
    }
}
