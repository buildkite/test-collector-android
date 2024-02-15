package com.buildkite.test.collector.android.example

import org.junit.Assert.fail
import org.junit.Ignore
import org.junit.Test

@Suppress("EmptyFunctionBlock")
class ExampleUnitTestsSet2 {

    @Test
    fun testWillPass() {}

    @Test
    fun testWillFail() {
        fail("Failing unit test")
    }

    @Test
    fun testWillFailWithException() {
        throw Exception("Failing unit test with custom exception")
    }

    @Test
    @Ignore("Skipped unit test")
    fun testWillBeSkipped() {}
}
