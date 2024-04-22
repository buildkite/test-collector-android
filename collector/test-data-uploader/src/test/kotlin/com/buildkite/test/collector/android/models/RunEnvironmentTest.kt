package com.buildkite.test.collector.android.models

import com.buildkite.test.collector.android.model.RunEnvironment
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import org.junit.Test

class RunEnvironmentTest {

    @Test
    fun runEnvironmentIncludesVersionNameAndCollector() {
        val gson = Gson()
        val jsonRunEnvironment = gson.toJson(RunEnvironment())
        val deserializedRunEnvironment = gson.fromJson(jsonRunEnvironment, RunEnvironment::class.java)

        assertEquals(
            RunEnvironment.VERSION_NAME,
            deserializedRunEnvironment.version
        )

        assertEquals(
            RunEnvironment.COLLECTOR_NAME,
            deserializedRunEnvironment.collector
        )
    }
}
