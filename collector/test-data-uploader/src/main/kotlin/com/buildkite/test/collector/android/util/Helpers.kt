package com.buildkite.test.collector.android.util

import java.util.UUID

internal object Helpers {
    fun generateUUIDString(): String {
        return UUID.randomUUID().toString()
    }
}
