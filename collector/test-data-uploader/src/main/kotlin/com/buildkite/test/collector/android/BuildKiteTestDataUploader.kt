package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.model.RunEnvironment
import com.buildkite.test.collector.android.model.TestData
import com.buildkite.test.collector.android.model.TestDetails
import com.buildkite.test.collector.android.model.TestUploadResponse
import com.buildkite.test.collector.android.network.api.DefaultTestUploaderApiFactory
import com.buildkite.test.collector.android.network.api.TestUploaderApiFactory
import com.buildkite.test.collector.android.util.CollectorUtils
import com.buildkite.test.collector.android.util.logger.Logger
import retrofit2.Response

/**
 * Manages the upload of test data to the Buildkite Test Analytics Suite.
 *
 * @property testSuiteApiToken The API token for authentication.
 * @property runEnvironment The test run environment configuration.
 * @property logger The logger for logging messages.
 */
class BuildKiteTestDataUploader(
    private val testSuiteApiToken: String,
    private val runEnvironment: RunEnvironment,
    private val logger: Logger = Logger()
) : TestDataUploader {
    private val testUploaderApiFactory: TestUploaderApiFactory = DefaultTestUploaderApiFactory()

    init {
        logger.debug { "TestDataUploader initialized with test analytics API token." }
        if (runEnvironment.ci != null) {
            logger.debug { "CI system detected: ${runEnvironment.ci}" }
        } else {
            logger.debug { "No CI system detected." }
        }
    }

    /**
     * Uploads test data to the Buildkite Test Analytics Suite.
     * The number of test data uploaded in a single request is constrained by [CollectorUtils.Uploader.TEST_DATA_UPLOAD_LIMIT].
     *
     * @param testCollection A list of [TestDetails] representing all the tests within the suite.
     */
    override fun uploadTestData(testCollection: List<TestDetails>) {
        val testData = prepareTestData(testCollection)
        uploadTestData(testData)
    }

    private fun prepareTestData(testCollection: List<TestDetails>): TestData {
        return TestData(
            format = "json",
            runEnvironment = runEnvironment,
            data = testCollection.take(CollectorUtils.Uploader.TEST_DATA_UPLOAD_LIMIT)
        )
    }

    private fun uploadTestData(testData: TestData) {
        logger.debug { "Uploading test analytics data." }

        runCatching {
            val testUploaderApi = testUploaderApiFactory.create(testSuiteApiToken)
            val uploadTestDataApiCall = testUploaderApi.uploadTestData(testData = testData)

            uploadTestDataApiCall.execute()
        }.onSuccess { testUploadResponse ->
            logApiResponse(testUploadResponse = testUploadResponse)
        }.onFailure { throwable ->
            logger.error { "Error uploading test analytics data: ${throwable.message}." }
        }
    }

    private fun logApiResponse(testUploadResponse: Response<TestUploadResponse>) {
        if (testUploadResponse.isSuccessful) {
            logger.info { "Test analytics data successfully uploaded. URL: ${testUploadResponse.body()?.runUrl}" }
        } else {
            logger.error {
                "Error uploading test analytics data. HTTP error code: ${testUploadResponse.code()}. Ensure the test suite API token is correct and properly configured."
            }
            logger.error { "Error response details: ${testUploadResponse.errorBody()?.string()}" }
        }
    }
}
