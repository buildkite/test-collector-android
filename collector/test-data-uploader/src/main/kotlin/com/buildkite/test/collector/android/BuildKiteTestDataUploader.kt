package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.model.RunEnvironment
import com.buildkite.test.collector.android.model.TestData
import com.buildkite.test.collector.android.model.TestDetails
import com.buildkite.test.collector.android.model.TestUploadResponse
import com.buildkite.test.collector.android.network.TestAnalyticsRetrofit
import com.buildkite.test.collector.android.network.api.TestUploaderApi
import com.buildkite.test.collector.android.util.CollectorUtils
import com.buildkite.test.collector.android.util.Logger
import retrofit2.Response

/**
 * Manages the upload of test data to the Buildkite Test Analytics Suite associated with the provided API token.
 *
 * @property testSuiteApiToken The API token for the test suite, necessary for authenticating requests with Test Analytics.
 *                             Test data will not be uploaded if this is null.
 * @property isDebugEnabled When true, enables logging to assist with debugging.
 */
class BuildKiteTestDataUploader(
    private val testSuiteApiToken: String?,
    private val isDebugEnabled: Boolean
) : TestDataUploader {
    private val logger =
        Logger(minLevel = if (isDebugEnabled) Logger.LogLevel.DEBUG else Logger.LogLevel.INFO)

    /**
     * Uploads test data to the Buildkite Test Analytics Suite.
     * The number of test data uploaded in a single request is constrained by [CollectorUtils.Uploader.TEST_DATA_UPLOAD_LIMIT].
     *
     * @param testCollection A list of [TestDetails] representing all the tests within the suite.
     */
    override fun uploadTestData(testCollection: List<TestDetails>) {
        val runEnvironment = RunEnvironment().getEnvironmentValues()

        val testData = TestData(
            format = "json",
            runEnvironment = runEnvironment,
            data = testCollection.take(CollectorUtils.Uploader.TEST_DATA_UPLOAD_LIMIT)
        )

        uploadTestData(testData = testData)
    }

    /**
     * Performs the actual upload of the provided test data to the Buildkite Test Analytics Suite.
     *
     * @param testData The test data to be uploaded.
     */
    private fun uploadTestData(testData: TestData) {
        if (testSuiteApiToken == null) {
            logger.info {
                "Incorrect or missing Test Suite API token. Please ensure the 'BUILDKITE_ANALYTICS_TOKEN' environment variable is set correctly to upload test data."
            }
            return
        }

        try {
            logger.debug { "Uploading test analytics data." }

            val testUploaderService =
                TestAnalyticsRetrofit.getRetrofitInstance(testSuiteApiToken = testSuiteApiToken)
                    .create(TestUploaderApi::class.java)
            val uploadTestDataApiCall = testUploaderService.uploadTestData(testData = testData)
            val testUploadResponse = uploadTestDataApiCall.execute()

            logApiResponse(testUploadResponse = testUploadResponse)
        } catch (e: Exception) {
            logger.error { "Error uploading test analytics data: ${e.message}." }
        }
    }

    private fun logApiResponse(testUploadResponse: Response<TestUploadResponse>) {
        if (testUploadResponse.isSuccessful) {
            logger.info { "Test analytics data successfully uploaded. URL: ${testUploadResponse.body()?.runUrl}" }
        } else {
            logger.error {
                "Error uploading test analytics data. HTTP error code: ${testUploadResponse.code()}. Ensure the test suite API token is correct and properly configured."
            }
            logger.debug { "Failed response details: ${testUploadResponse.errorBody()?.string()}" }
        }
    }
}
