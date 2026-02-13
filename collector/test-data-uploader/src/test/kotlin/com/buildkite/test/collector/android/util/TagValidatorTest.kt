package com.buildkite.test.collector.android.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TagValidatorTest {

    @Test
    fun validateKeyAcceptsValidKeys() {
        assertTrue(TagValidator.validateKey("env"))
        assertTrue(TagValidator.validateKey("language.name"))
        assertTrue(TagValidator.validateKey("host_arch"))
        assertTrue(TagValidator.validateKey("my-tag"))
        assertTrue(TagValidator.validateKey("a123"))
    }

    @Test
    fun validateKeyRejectsBlankKeys() {
        assertFalse(TagValidator.validateKey(""))
        assertFalse(TagValidator.validateKey("  "))
    }

    @Test
    fun validateKeyRejectsKeysStartingWithNonLetter() {
        assertFalse(TagValidator.validateKey("1abc"))
        assertFalse(TagValidator.validateKey("_abc"))
        assertFalse(TagValidator.validateKey(".abc"))
        assertFalse(TagValidator.validateKey("-abc"))
    }

    @Test
    fun validateKeyRejectsKeysWithInvalidCharacters() {
        assertFalse(TagValidator.validateKey("key!"))
        assertFalse(TagValidator.validateKey("key value"))
        assertFalse(TagValidator.validateKey("key@val"))
    }

    @Test
    fun validateKeyRejectsKeysTooLong() {
        val longKey = "a" + "b".repeat(63)
        assertFalse(TagValidator.validateKey(longKey))
    }

    @Test
    fun validateValueAcceptsValidValues() {
        assertTrue(TagValidator.validateValue("production"))
        assertTrue(TagValidator.validateValue("a"))
    }

    @Test
    fun validateValueRejectsBlankValues() {
        assertFalse(TagValidator.validateValue(""))
        assertFalse(TagValidator.validateValue("  "))
    }

    @Test
    fun validateValueRejectsValuesTooLong() {
        val longValue = "a".repeat(128)
        assertFalse(TagValidator.validateValue(longValue))
    }

    @Test
    fun validateFiltersInvalidEntries() {
        val tags = mapOf(
            "valid" to "value",
            "1invalid" to "value",
            "also-valid" to "ok",
            "good" to "  "
        )
        val result = TagValidator.validate(tags)
        assertEquals(mapOf("valid" to "value", "also-valid" to "ok"), result)
    }

    @Test
    fun validateEnforcesMaxTenTags() {
        val tags = (1..12).associate { "tag$it" to "value$it" }
        val result = TagValidator.validate(tags)
        assertEquals(10, result.size)
        assertTrue(result.containsKey("tag1"))
        assertTrue(result.containsKey("tag10"))
        assertFalse(result.containsKey("tag11"))
        assertFalse(result.containsKey("tag12"))
    }

    @Test
    fun validateRemovesDotPrefixConflicts() {
        val tags = mapOf("service" to "x", "service.name" to "y")
        val result = TagValidator.validate(tags)
        assertFalse(result.containsKey("service"))
        assertEquals("y", result["service.name"])
    }

    @Test
    fun mergeEnvironmentTakesPrecedence() {
        val programmatic = mapOf("a" to "1")
        val environment = mapOf("a" to "2")
        val result = TagValidator.merge(programmatic, environment)
        assertEquals(mapOf("a" to "2"), result)
    }

    @Test
    fun mergeCombinesBothMaps() {
        val programmatic = mapOf("a" to "1")
        val environment = mapOf("b" to "2")
        val result = TagValidator.merge(programmatic, environment)
        assertEquals(mapOf("a" to "1", "b" to "2"), result)
    }

    @Test
    fun validateAcceptsEmptyMap() {
        val result = TagValidator.validate(emptyMap())
        assertTrue(result.isEmpty())
    }

    @Test
    fun validateKeyAcceptsDotsAndHyphens() {
        assertTrue(TagValidator.validateKey("cloud.provider"))
        assertTrue(TagValidator.validateKey("host-type"))
    }
}
