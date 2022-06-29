package com.buildkite.test_data_uploader.domain.model.api

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class RunEnvironment(
    @SerializedName(value = "CI") val ci: String? = null,
    @SerializedName(value = "key") val key: String = UUID.randomUUID().toString(),
    @SerializedName(value = "url") val url: String? = null,
    @SerializedName(value = "branch") val branch: String? = null,
    @SerializedName(value = "commit_sha") val commitSha: String? = null,
    @SerializedName(value = "number") val number: String? = null,
    @SerializedName(value = "job_id") val jobId: String? = null,
    @SerializedName(value = "message") val message: String? = null
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
