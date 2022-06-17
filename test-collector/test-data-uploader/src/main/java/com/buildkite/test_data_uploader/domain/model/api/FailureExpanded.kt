package com.buildkite.test_data_uploader.domain.model.api

data class FailureExpanded(
    val backtrace: List<String>? = emptyList(),
    val expanded: List<String?>? = emptyList()
)
