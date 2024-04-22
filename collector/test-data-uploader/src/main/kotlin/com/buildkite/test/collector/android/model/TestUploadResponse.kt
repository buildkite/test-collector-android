package com.buildkite.test.collector.android.model

import com.google.gson.annotations.SerializedName

/**
 * Represents the response received from the analytics API after uploading test results.
 *
 * @property id Test response identifier.
 * @property runId Identifier for the test run associated with the upload.
 * @property queued The number of test results queued for processing.
 * @property skipped The number of test results uploaded but not processed.
 * @property errors List of error messages, if any, that occurred during the upload.
 * @property runUrl The URL to access the details of the test run.
 */
internal data class TestUploadResponse(
    @SerializedName("id") val id: String,
    @SerializedName("run_id") val runId: String,
    @SerializedName("queued") val queued: Int,
    @SerializedName("skipped") val skipped: Int,
    @SerializedName("errors") val errors: List<String>,
    @SerializedName("run_url") val runUrl: String,
)
