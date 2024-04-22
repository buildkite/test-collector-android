package com.buildkite.test.collector.android.model

import com.google.gson.annotations.SerializedName

/**
 * Represents the payload for Buildkite test analytics API.
 *
 * @property format Specifies the format for the upload data.
 * @property runEnvironment Context of the test execution environment.
 *           Test results with matching run environments will be grouped together by the analytics API.
 * @property data List of [TestDetails] providing individual test outcomes and related information.
 */
internal data class TestData(
    @SerializedName("format") val format: String = "json",
    @SerializedName("run_env") val runEnvironment: RunEnvironment,
    @SerializedName("data") val data: List<TestDetails>
)
