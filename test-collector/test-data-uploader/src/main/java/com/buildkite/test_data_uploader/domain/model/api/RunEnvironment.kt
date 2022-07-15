package com.buildkite.test_data_uploader.domain.model.api

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class RunEnvironment(
    @SerializedName("CI") val ci: String? = null,
    @SerializedName("key") val key: String = UUID.randomUUID().toString(),
    @SerializedName("url") val url: String? = null,
    @SerializedName("branch") val branch: String? = null,
    @SerializedName("commit_sha") val commitSha: String? = null,
    @SerializedName("number") val number: String? = null,
    @SerializedName("job_id") val jobId: String? = null,
    @SerializedName("message") val message: String? = null
) {
    fun getEnvironmentValues(): RunEnvironment {
        val buildKiteRunEnvironment: RunEnvironment? = null
        val gitHubActionsRunEnvironment: RunEnvironment? = null
        val circleCiRunEnvironment: RunEnvironment? = null

        val localRunEnvironment = RunEnvironment(
            ci = ci,
            key = key,
            url = url,
            branch = branch,
            commitSha = commitSha,
            number = number,
            jobId = jobId,
            message = message
        )

        return buildKiteRunEnvironment
            ?: gitHubActionsRunEnvironment
            ?: circleCiRunEnvironment
            ?: localRunEnvironment
    }
}
