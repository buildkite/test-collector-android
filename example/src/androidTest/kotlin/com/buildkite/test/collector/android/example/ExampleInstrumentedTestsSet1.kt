package com.buildkite.test.collector.android.example

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("EmptyFunctionBlock")
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTestsSet1 {

    @Test
    fun testWillPass() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.buildkite.test.collector.android.example", appContext.packageName)
    }

    @Test
    fun testWillFail() {
        fail("Failing instrumented test")
    }

    @Test
    fun testWillFailWithException() {
        throw Exception("Failing instrumented test with custom exception")
    }

    @Test
    @Ignore("Skipped instrumented test")
    fun testWillBeSkipped() {}
}
