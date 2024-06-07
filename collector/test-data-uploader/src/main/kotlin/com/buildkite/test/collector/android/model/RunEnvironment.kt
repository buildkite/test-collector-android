package com.buildkite.test.collector.android.model

import com.buildkite.test.collector.android.util.CollectorUtils.generateUUIDString
import com.google.gson.annotations.SerializedName

/**
 * Represents information about the environment in which the test run is performed. Suitable for identifying test runs across CI and local environments.
 *
 * @property key A unique identifier for the test run.
 *           It's the only required field, especially for local tests where CI-specific fields aren't relevant.
 * @property ci The continuous integration platform name.
 * @property url The URL associated with the test run.
 * @property branch The branch or reference for this build.
 * @property commitSha The commit hash for the head of the branch.
 * @property number The build number.
 * @property jobId The job UUID.
 * @property message The commit message for the head of the branch.
 * @property version The current version of the collector.
 * @property collector The current name of the collector.
 */
data class RunEnvironment(
    @SerializedName("CI") val ci: String? = null,
    @SerializedName("key") val key: String = generateUUIDString(),
    @SerializedName("url") val url: String? = null,
    @SerializedName("branch") val branch: String? = null,
    @SerializedName("commit_sha") val commitSha: String? = null,
    @SerializedName("number") val number: String? = null,
    @SerializedName("job_id") val jobId: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("version") val version: String = VERSION_NAME,
    @SerializedName("collector") val collector: String = COLLECTOR_NAME
) {
    companion object {
        // When bumping version, update VERSION_NAME to match new version
        // Used for uploading correct library version
        const val VERSION_NAME = "0.3.0"
        const val COLLECTOR_NAME = "android-buildkite-test-collector"
    }
}
