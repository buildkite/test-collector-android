package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.model.TestData
import com.buildkite.test.collector.android.model.TestDetails
import com.buildkite.test.collector.android.model.TestUploadResponse
import com.buildkite.test.collector.android.network.TestAnalyticsRetrofit
import com.buildkite.test.collector.android.network.api.TestUploaderApi
import com.buildkite.test.collector.android.tracer.environment.TestEnvironmentProvider
import com.buildkite.test.collector.android.util.CollectorUtils
import com.buildkite.test.collector.android.util.Logger
import retrofit2.Response

/**
 * Manages the upload of test data to the Buildkite Test Analytics Suite using the provided environment values.
 *
 * This class fetches the necessary environment configuration from a [TestEnvironmentProvider].
 *
 * @property testEnvironmentProvider Provides the environment configuration needed for uploading test data.
 */
class BuildKiteTestDataUploader(
    private val testEnvironmentProvider: TestEnvironmentProvider
) : TestDataUploader {
    private val testSuiteApiToken = testEnvironmentProvider.testSuiteApiToken

    private val logger =
        Logger(minLevel = if (testEnvironmentProvider.isDebugEnabled) Logger.LogLevel.DEBUG else Logger.LogLevel.INFO)

    init {
        logger.debug { "BuildKiteTestDataUploader: Test RunEnvironment is: ${testEnvironmentProvider.getRunEnvironment()}" }
    }

    /**
     * Uploads test data to the Buildkite Test Analytics Suite.
     * The number of test data uploaded in a single request is constrained by [CollectorUtils.Uploader.TEST_DATA_UPLOAD_LIMIT].
     *
     * @param testCollection A list of [TestDetails] representing all the tests within the suite.
     */
    override fun uploadTestData(testCollection: List<TestDetails>) {
        val testData = TestData(
            format = "json",
            runEnvironment = testEnvironmentProvider.getRunEnvironment(),
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
            logger.error { "Failed response details: ${testUploadResponse.errorBody()?.string()}" }
        }
    }
}
