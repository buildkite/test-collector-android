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
    fun setExecutionTagEnforcesMaxTen() {
        for (i in 1..10) {
            observer.setExecutionTag("key$i", "value$i")
        }
        assertEquals(10, observer.executionTags.size)

        observer.setExecutionTag("key11", "value11")
        assertEquals(10, observer.executionTags.size)
        assertTrue("key11" !in observer.executionTags)
    }

    @Test
    fun setExecutionTagAllowsOverwriteAtMaxCapacity() {
        for (i in 1..10) {
            observer.setExecutionTag("key$i", "value$i")
        }
        observer.setExecutionTag("key5", "updated")
        assertEquals(10, observer.executionTags.size)
        assertEquals("updated", observer.executionTags["key5"])
    }

    @Test
    fun setExecutionTagRejectsInvalidKey() {
        observer.setExecutionTag("1invalid", "value")
        assertTrue(observer.executionTags.isEmpty())
    }

    @Test
    fun setExecutionTagRejectsBlankValue() {
        observer.setExecutionTag("valid", "  ")
        assertTrue(observer.executionTags.isEmpty())
    }

    @Test
    fun resetClearsTags() {
        observer.setExecutionTag("env", "prod")
        observer.reset()
        assertTrue(observer.executionTags.isEmpty())
    }
}
