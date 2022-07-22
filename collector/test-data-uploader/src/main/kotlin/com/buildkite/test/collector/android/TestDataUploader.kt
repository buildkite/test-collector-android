package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.network.RetrofitInstance
import com.buildkite.test.collector.android.network.api.TestUploaderApi
import com.buildkite.test.collector.android.models.RunEnvironment
import com.buildkite.test.collector.android.models.TestData
import com.buildkite.test.collector.android.models.TestDetails
import com.buildkite.test.collector.android.models.TestResponse
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
            logApiResponse(executeApiCall)
        }
    }

    private fun logApiResponse(executeApiCall: Response<TestResponse>) {
        when (val apiResponseCode = executeApiCall.raw().code) {
            202 -> println("\nTest analytics data successfully uploaded to the BuildKite Test Suite. - ${executeApiCall.body()?.runUrl}")
            else -> println("\nError uploading test analytics data to the BuildKite Test Suite. Error code: $apiResponseCode")
        }
    }
}
