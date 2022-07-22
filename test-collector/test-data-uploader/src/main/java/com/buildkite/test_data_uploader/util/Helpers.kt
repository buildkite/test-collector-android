package com.buildkite.test_data_uploader.util

import java.util.UUID

object Helpers {

    fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }
}
