package com.buildkite.test.collector.android

/**
 * Configuration object for the Buildkite Test Analytics collector.
 *
 * Use [configure] to set upload-level tags programmatically before tests run.
 * These tags apply to all test executions in the upload.
 *
 * ```kotlin
 * BuildkiteTestCollector.configure(
 *     uploadTags = mapOf("environment" to "staging", "language.name" to "kotlin")
 * )
 * ```
 */
object BuildkiteTestCollector {
    @Volatile
    private var uploadTags: Map<String, String> = emptyMap()

    /**
     * Configures upload-level tags for the test collector.
     *
     * @param uploadTags Tags that apply to all test executions in the upload.
     *        These are merged with tags from the `BUILDKITE_ANALYTICS_TAGS` environment variable,
     *        with environment variable values taking precedence on key collision.
     */
    fun configure(uploadTags: Map<String, String> = emptyMap()) {
        this.uploadTags = uploadTags
    }

    internal fun getProgrammaticUploadTags(): Map<String, String> = uploadTags
}
