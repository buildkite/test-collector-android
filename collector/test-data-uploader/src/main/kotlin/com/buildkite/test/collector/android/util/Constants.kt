package com.buildkite.test.collector.android.util

object Constants {

    object Collector {
        const val TEST_DATA_UPLOAD_LIMIT = 5000
    }

    object Network {
        const val BASE_URL = "https://analytics-api.buildkite.com"
        const val TEST_UPLOADER_ENDPOINT = "/v1/uploads"
    }
}
