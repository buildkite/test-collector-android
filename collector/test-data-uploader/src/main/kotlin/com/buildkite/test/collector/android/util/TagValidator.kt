package com.buildkite.test.collector.android.util

object TagValidator {

    internal const val MAX_TAGS = 10
    private const val MAX_KEY_BYTES = 64
    private const val MAX_VALUE_BYTES = 128

    private val KEY_PATTERN = Regex("^[a-zA-Z][a-zA-Z0-9_.-]*$")

    fun validateKey(key: String): Boolean {
        return key.isNotBlank() &&
            KEY_PATTERN.matches(key) &&
            key.toByteArray(Charsets.UTF_8).size < MAX_KEY_BYTES
    }

    fun validateValue(value: String): Boolean {
        return value.isNotBlank() &&
            value.toByteArray(Charsets.UTF_8).size < MAX_VALUE_BYTES
    }

    fun validate(tags: Map<String, String>): Map<String, String> {
        val filtered = tags.filter { (key, value) -> validateKey(key) && validateValue(value) }
            .entries
            .take(MAX_TAGS)
            .associate { it.key to it.value }

        val keys = filtered.keys
        return filtered.filterKeys { key ->
            !keys.any { other -> other != key && other.startsWith("$key.") }
        }
    }

    fun merge(programmatic: Map<String, String>, environment: Map<String, String>): Map<String, String> {
        return programmatic + environment
    }
}
