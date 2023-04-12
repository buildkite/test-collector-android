package com.buildkite.test.collector.android.models

import com.buildkite.test.collector.android.util.Helpers.generateUUID
import com.google.gson.annotations.SerializedName

data class RunEnvironment(
    @SerializedName("CI") val ci: String? = null,
    @SerializedName("key") val key: String = generateUUID(),
    @SerializedName("url") val url: String? = null,
    @SerializedName("branch") val branch: String? = null,
    @SerializedName("commit_sha") val commitSha: String? = null,
    @SerializedName("number") val number: String? = null,
    @SerializedName("job_id") val jobId: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("version") val version: String = VERSION_NAME,
    @SerializedName("collector") val collector: String = COLLECTOR_NAME
) {
    fun getEnvironmentValues(): RunEnvironment {
        val buildKiteRunEnvironment: RunEnvironment? = null
        val gitHubActionsRunEnvironment: RunEnvironment? = null
        val circleCiRunEnvironment: RunEnvironment? = null
        val genericCiRunEnvironment: RunEnvironment? = null

        val localRunEnvironment = RunEnvironment(
            ci = ci,
            key = key,
            url = url,
            branch = branch,
            commitSha = commitSha,
            number = number,
            jobId = jobId,
            message = message,
            version = version,
            collector = collector
        )

        val ciRunEnvironment: RunEnvironment? = buildKiteRunEnvironment
            ?: gitHubActionsRunEnvironment
            ?: circleCiRunEnvironment
            ?: genericCiRunEnvironment

        return ciRunEnvironment
            ?: localRunEnvironment
    }

    companion object {
        // When bumping version, update VERSION_NAME to match new version
        // Used for uploading correct library version
        val VERSION_NAME = "0.2.0-SNAPSHOT"
        val COLLECTOR_NAME = "android-buildkite-test-collector"
    }
}
