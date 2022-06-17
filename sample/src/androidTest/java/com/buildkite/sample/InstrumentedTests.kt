package com.buildkite.sample

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTests {

    @Test
    fun passedInstrumentedTestInExampleProject() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.buildkite.sample", appContext.packageName)
    }

    @Test
    fun failedInstrumentedTestInExampleProject() {
        fail("Failing instrumented test in Example Project")
    }

    @Test
    fun failedInstrumentedTestWithErrorInExampleProject() {
        throw Exception("Failing instrumented test with error in Example Project")
    }

    @Test
    @Ignore("Skipping instrumented test in Example Project")
    fun skippedInstrumentedTestInExampleProject() {}
}
