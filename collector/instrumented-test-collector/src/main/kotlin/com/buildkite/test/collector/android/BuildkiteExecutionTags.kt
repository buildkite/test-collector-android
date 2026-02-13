package com.buildkite.test.collector.android

import com.buildkite.test.collector.android.tracer.TestObserver

/**
 * Provides execution-level tagging for instrumented tests.
 *
 * Call [tagExecution] during a test to attach tags to the current test execution.
 * Tags are automatically collected and uploaded with the test results.
 *
 * ```kotlin
 * @Test
 * fun myTest() {
 *     BuildkiteExecutionTags.tagExecution("user_type", "premium")
 *     // ... test code
 * }
 * ```
 */
object BuildkiteExecutionTags {
    @Volatile
    private var currentObserver: TestObserver? = null

    internal fun bind(observer: TestObserver) {
        currentObserver = observer
    }

    internal fun unbind(observer: TestObserver) {
        if (currentObserver === observer) {
            currentObserver = null
        }
    }

    /**
     * Sets an execution-level tag on the currently running test.
     *
     * @param key The tag key. Must start with a letter, contain only letters, numbers, underscores, hyphens, and periods, and be less than 64 bytes.
     * @param value The tag value. Must not be blank and be less than 128 bytes.
     */
    fun tagExecution(key: String, value: String) {
        currentObserver?.setExecutionTag(key, value)
    }
}
