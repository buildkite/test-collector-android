package com.buildkite.test_data_uploader

import com.buildkite.test_data_uploader.data.network.RetrofitInstance
import com.buildkite.test_data_uploader.data.network.TestUploaderApi
import com.buildkite.test_data_uploader.models.RunEnvironment
import com.buildkite.test_data_uploader.models.TestData
import com.buildkite.test_data_uploader.models.TestDetails
import com.buildkite.test_data_uploader.models.TestResponse
import retrofit2.Response

class TestDataUploader(
    val testSuiteApiToken: String,
    val isDebugEnabled: Boolean
) {
    fun configureUploadData(testCollection: List<TestDetails>) {
        val runEnvironment = RunEnvironment().getEnvironmentValues()

        val testData = TestData(
            format = "json",
            runEnvironment = runEnvironment,
            data = testCollection
        )

        uploadTestData(testData = testData)
    }

    private fun uploadTestData(testData: TestData) {
        val retroService = RetrofitInstance.getRetrofitInstance(testSuiteApiToken = testSuiteApiToken)
            .create(TestUploaderApi::class.java)
        val uploadTestDataApiCall = retroService.uploadTestData(testData = testData)

        val executeApiCall = uploadTestDataApiCall.execute()

        if (isDebugEnabled) {
            printResponseLog(executeApiCall)
        }
    }

    private fun printResponseLog(executeApiCall: Response<TestResponse>) {
        when (val apiResponseCode = executeApiCall.raw().code) {
            202 -> println("\nTest analytics data successfully uploaded to the BuildKite Test Suite. - ${executeApiCall.body()?.runUrl}")
            else -> println("\nError uploading test analytics data to the BuildKite Test Suite. Error code: $apiResponseCode")
        }
    }
}
