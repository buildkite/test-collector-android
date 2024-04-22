package com.buildkite.test.collector.android.util

import java.util.UUID

internal object CollectorUtils {

    object Uploader {
        /**
         * The maximum number of test results that can be uploaded in a single request.
         * This limit ensures the upload request does not exceed the size constraints set by the Test Analytics API.
         */
        const val TEST_DATA_UPLOAD_LIMIT = 5000
    }

    object Network {
        const val BASE_URL = "https://analytics-api.buildkite.com"
        const val TEST_UPLOADER_ENDPOINT = "/v1/uploads"
    }

    fun generateUUIDString(): String {
        return UUID.randomUUID().toString()
    }
}
