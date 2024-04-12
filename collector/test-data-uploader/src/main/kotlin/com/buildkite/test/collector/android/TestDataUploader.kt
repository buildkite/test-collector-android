package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.model.RunEnvironment
import com.buildkite.test.collector.android.model.TestData
import com.buildkite.test.collector.android.model.TestDetails
import com.buildkite.test.collector.android.model.TestUploadResponse
import com.buildkite.test.collector.android.network.TestAnalyticsRetrofit
import com.buildkite.test.collector.android.network.api.TestUploaderApi
import com.buildkite.test.collector.android.util.CollectorUtils.Uploader
import retrofit2.Response

/**
 * Manages the upload of test data to the Buildkite Test Analytics Suite associated with the provided API token.
 *
 * @property testSuiteApiToken The API token for the test suite, necessary for authenticating requests with Test Analytics.
 *                             Test data will not be uploaded if this is null.
 * @property isDebugEnabled When true, enables logging to assist with debugging.
 */
class TestDataUploader(
    private val testSuiteApiToken: String?,
    private val isDebugEnabled: Boolean
) {
    /**
     * Configures and uploads test data.
     * The number of test data uploaded in a single request is constrained by [Uploader.TEST_DATA_UPLOAD_LIMIT].
     *
     * @param testCollection A list of [TestDetails] representing all the tests within the suite.
     * */
    fun configureUploadData(testCollection: List<TestDetails>) {
        val runEnvironment = RunEnvironment().getEnvironmentValues()

        val testData = TestData(
            format = "json",
            runEnvironment = runEnvironment,
            data = testCollection.take(Uploader.TEST_DATA_UPLOAD_LIMIT)
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
            val testUploaderService =
                TestAnalyticsRetrofit.getRetrofitInstance(testSuiteApiToken = testSuiteApiToken)
                    .create(TestUploaderApi::class.java)
            val uploadTestDataApiCall = testUploaderService.uploadTestData(testData = testData)

            val executeApiCall = uploadTestDataApiCall.execute()

            if (isDebugEnabled) {
                logApiResponse(executeApiCall)
            }
        }
    }

    private fun logApiResponse(executeApiCall: Response<TestUploadResponse>) {
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
