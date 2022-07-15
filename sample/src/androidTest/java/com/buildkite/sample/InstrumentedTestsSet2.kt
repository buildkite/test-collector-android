package com.buildkite.sample

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentedTestsSet2 {

    @Test
    fun testWillPass() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.buildkite.sample", appContext.packageName)
    }

    @Test
    fun testWillFail() {
        fail("Failing instrumented test in: InstrumentedTestsSet2")
    }

    @Test
    fun testWillFailWithException() {
        throw Exception("Failing instrumented test in: InstrumentedTestsSet2")
    }

    @Test
    @Ignore("Skipped instrumented test in: InstrumentedTestsSet2")
    fun testWillBeSkipped() {}
}
