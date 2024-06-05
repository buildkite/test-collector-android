package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.model.TestDetails

/**
 * Interface for uploading test data to a test analytics service.
 */
interface TestDataUploader {
    /**
     * Uploads test data to the test analytics service.
     *
     * @param testCollection A list of [TestDetails] representing all the tests within the suite.
     */
    fun uploadTestData(testCollection: List<TestDetails>)
}
