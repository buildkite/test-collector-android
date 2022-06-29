package com.buildkite.sample

import org.junit.Assert.fail
import org.junit.Ignore
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTests {

    @Test
    fun passedUnitTestInExampleProject() {}

    @Test
    fun failedUnitTestInExampleProject() {
        fail("Failing unit test in Example Project")
    }

    @Test
    fun failedUnitTestWithErrorInExampleProject() {
        throw Exception("Failing unit test with error in Example Project")
    }

    @Test
    @Ignore("Skipping unit test in Example Project")
    fun skippedUnitTestInExampleProject() {}
}
