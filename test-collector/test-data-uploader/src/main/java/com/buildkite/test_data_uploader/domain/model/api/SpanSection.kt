package com.buildkite.test_data_uploader.domain.model.api

import com.google.gson.annotations.SerializedName

enum class SpanSection {
    @SerializedName(value = "top") Top,
    @SerializedName(value = "sql") Sql,
    @SerializedName(value = "http") Http,
    @SerializedName(value = "sleep") Sleep,
    @SerializedName(value = "annotation") Annotation
}
