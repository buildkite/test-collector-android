package com.buildkite.test.collector.android.model

import com.google.gson.annotations.SerializedName

/**
 * Represents [TestSpan] category, classifying the type of operation or activity within a test run.
 */
enum class TestSpanSection {
    @SerializedName(value = "top")
    Top,

    @SerializedName(value = "sql")
    Sql,

    @SerializedName(value = "http")
    Http,

    @SerializedName(value = "sleep")
    Sleep,

    @SerializedName(value = "annotation")
    Annotation
}
