package com.buildkite.test.collector.android.model

import com.buildkite.test.collector.android.util.CollectorUtils.generateUUIDString
import com.google.gson.annotations.SerializedName

/**
 * Represents a single test run with comprehensive details.
 *
 * @property id A unique identifier for this test result, defaulting to a generated UUID string.
 *              Duplicate IDs are ignored by the Test Analytics database.
 * @property scope Specifies a group or topic for the test.
 * @property name The name or description of the test.
 * @property location The file and line number where the test originates.
 * @property fileName The file where the test is defined.
 * @property result The outcome of the test, specified as a [TestOutcome].
 * @property failureReason A short description of the failure.
 * @property failureExpanded Detailed failure information as a list of [TestFailureExpanded] objects.
 * @property history The span information capturing the duration and execution details of the test.
 */
data class TestDetails(
    @SerializedName("id") val id: String = generateUUIDString(),
    @SerializedName("scope") val scope: String?,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String?,
    @SerializedName("file_name") val fileName: String?,
    @SerializedName("result") val result: TestOutcome?,
    @SerializedName("failure_reason") val failureReason: String?,
    @SerializedName("failure_expanded") val failureExpanded: List<TestFailureExpanded>? = null,
    @SerializedName("history") val history: TestHistory
)
