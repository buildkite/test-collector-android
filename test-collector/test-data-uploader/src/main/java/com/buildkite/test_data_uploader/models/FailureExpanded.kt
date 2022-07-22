package com.buildkite.test_data_uploader.models

import com.google.gson.annotations.SerializedName

data class FailureExpanded(
    @SerializedName("backtrace") val backtrace: List<String>? = emptyList(),
    @SerializedName("expanded") val expanded: List<String>? = emptyList()
)
