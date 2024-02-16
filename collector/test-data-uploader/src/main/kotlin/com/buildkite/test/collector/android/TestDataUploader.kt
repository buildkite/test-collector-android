package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.models.RunEnvironment
import com.buildkite.test.collector.android.models.TestData
import com.buildkite.test.collector.android.models.TestDetails
import com.buildkite.test.collector.android.models.TestResponse
import com.buildkite.test.collector.android.network.RetrofitInstance
import com.buildkite.test.collector.android.network.api.TestUploaderApi
import com.buildkite.test.collector.android.util.Constants.Collector
import retrofit2.Response

class TestDataUploader(
    private val testSuiteApiToken: String?,
    private val isDebugEnabled: Boolean
) {
    fun configureUploadData(testCollection: List<TestDetails>) {
        val runEnvironment = RunEnvironment().getEnvironmentValues()

        val testData = TestData(
            format = "json",
            runEnvironment = runEnvironment,
            data = testCollection.take(Collector.TEST_DATA_UPLOAD_LIMIT)
        )

        uploadTestData(testData = testData)
    }

    private fun uploadTestData(testData: TestData) {
        if (testSuiteApiToken == null) {
            println(
                "Buildkite test suite API token is missing. " +
                    "Please set up your API token environment variable to upload the analytics data. Follow [README] for further information."
            )
        } else {
            val retroService = RetrofitInstance.getRetrofitInstance(testSuiteApiToken = testSuiteApiToken)
                .create(TestUploaderApi::class.java)
            val uploadTestDataApiCall = retroService.uploadTestData(testData = testData)

            val executeApiCall = uploadTestDataApiCall.execute()

            if (isDebugEnabled) {
                logApiResponse(executeApiCall)
            }
        }
    }

    private fun logApiResponse(executeApiCall: Response<TestResponse>) {
        when (val apiResponseCode = executeApiCall.raw().code) {
            202 -> println(
                "\nTest analytics data successfully uploaded to the BuildKite Test Suite. - ${executeApiCall.body()?.runUrl}"
            )
            else -> println(
                "\nError uploading test analytics data to the BuildKite Test Suite. Error code: $apiResponseCode! Ensure that the test suite API Token is correct."
            )
        }
    }
}
