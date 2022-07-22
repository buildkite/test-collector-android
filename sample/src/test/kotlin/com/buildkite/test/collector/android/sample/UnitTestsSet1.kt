package com.buildkite.test.collector.android.sample

import org.junit.Assert.fail
import org.junit.Ignore
import org.junit.Test

class UnitTestsSet1 {

    @Test
    fun testWillPass() {}

    @Test
    fun testWillFail() {
        fail("Failing unit test in: UnitTestsSet1")
    }

    @Test
    fun testWillFailWithException() {
        throw Exception("Failing unit test with error in: UnitTestsSet1")
    }

    @Test
    @Ignore("Skipped unit test in: UnitTestsSet1")
    fun testWillBeSkipped() {}
}
