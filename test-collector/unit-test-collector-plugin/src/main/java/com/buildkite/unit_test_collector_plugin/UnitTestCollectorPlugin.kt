package com.buildkite.unit_test_collector_plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class UnitTestCollectorPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.task("Hello") {
            it.doLast {
                println("Hello from Unit Test Collector Plugin!")
            }
        }
    }
}
