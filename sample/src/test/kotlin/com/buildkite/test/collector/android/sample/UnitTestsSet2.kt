package com.buildkite.test.collector.android.sample

import org.junit.Assert.fail
import org.junit.Ignore
import org.junit.Test

class UnitTestsSet2 {

    @Test
    fun testWillPass() {}

    @Test
    fun testWillFail() {
        fail("Failing unit test in: UnitTestsSet2")
    }

    @Test
    fun testWillFailWithException() {
        throw Exception("Failing unit test with error in: UnitTestsSet2")
    }

    @Test
    @Ignore("Skipped unit test in: UnitTestsSet2")
    fun testWillBeSkipped() {}
}
