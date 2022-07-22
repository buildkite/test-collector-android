package com.buildkite.test.collector.android.util

import java.util.UUID

object Helpers {

    fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }
}
