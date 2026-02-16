package com.buildkite.test.collector.android.tracer

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BuildkiteTestObserverTagsTest {

    private lateinit var observer: BuildkiteTestObserver

    @Before
    fun setUp() {
        observer = BuildkiteTestObserver()
    }

    @Test
    fun executionTagsInitiallyEmpty() {
        assertTrue(observer.executionTags.isEmpty())
    }

    @Test
    fun setExecutionTagStoresTag() {
        observer.setExecutionTag("env", "prod")
        assertEquals(mapOf("env" to "prod"), observer.executionTags)
    }

    @Test
    fun setExecutionTagOverwritesExistingKey() {
        observer.setExecutionTag("env", "staging")
        observer.setExecutionTag("env", "prod")
        assertEquals(mapOf("env" to "prod"), observer.executionTags)
    }

    @Test
    fun resetClearsTags() {
        observer.setExecutionTag("env", "prod")
        observer.reset()
        assertTrue(observer.executionTags.isEmpty())
    }
}
