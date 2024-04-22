package com.buildkite.test.collector.android.model

import com.google.gson.annotations.SerializedName

/**
 * Represents additional information relevant to a specific [TestSpan].
 *
 * Depending on the [TestSpanSection], different information is provided.
 *
 * @property method The HTTP request method used (e.g., GET, POST). Required when [TestSpanSection] is [TestSpanSection.Http].
 * @property url The URL targeted by the HTTP request. Required when [TestSpanSection] is [TestSpanSection.Http].
 * @property library The library or tool used to make the HTTP request. Required when [TestSpanSection] is [TestSpanSection.Http].
 * @property query The SQL query executed. Required when [TestSpanSection] is [TestSpanSection.Sql].
 */
data class TestSpanDetail(
    @SerializedName("method") val method: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("lib") val library: String? = null,
    @SerializedName("query") val query: String? = null,
)
