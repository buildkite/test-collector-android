package com.buildkite.test.collector.android.tracer.environment

import com.buildkite.test.collector.android.model.RunEnvironment
import com.buildkite.test.collector.android.util.takeIfValid

/**
 * Base implementation of [TestEnvironmentProvider], providing methods to fetch environment values from various sources
 * such as system properties or Android Bundle arguments.
 */
abstract class BaseTestEnvironmentProvider : TestEnvironmentProvider {
    protected abstract fun getEnvironmentValue(key: String): String?

    private fun safeEnvValue(key: String): String? =
        getEnvironmentValue(key).takeIfValid()

    final override val testSuiteApiToken: String?
        get() = safeEnvValue(key = TestEnvironmentValue.BUILDKITE_ANALYTICS_TOKEN)

    final override val isDebugEnabled: Boolean
        get() = safeEnvValue(key = TestEnvironmentValue.BUILDKITE_ANALYTICS_DEBUG_ENABLED).toBoolean()

    final override fun getRunEnvironment(defaultKey: String): RunEnvironment =
        getGitHubActionsEnvironment()
            ?: getBuildkiteEnvironment()
            ?: getCircleCiEnvironment()
            ?: getLocalEnvironment(runKey = defaultKey)

    private fun getBuildkiteEnvironment(): RunEnvironment? {
        val buildId = safeEnvValue(key = TestEnvironmentValue.BUILDKITE_BUILD_ID)
            ?: return null

        return RunEnvironment(
            ci = "buildkite",
            key = buildId,
            url = safeEnvValue(key = TestEnvironmentValue.BUILDKITE_BUILD_URL),
            branch = safeEnvValue(key = TestEnvironmentValue.BUILDKITE_BRANCH),
            commitSha = safeEnvValue(key = TestEnvironmentValue.BUILDKITE_COMMIT),
            number = safeEnvValue(key = TestEnvironmentValue.BUILDKITE_BUILD_NUMBER),
            jobId = safeEnvValue(key = TestEnvironmentValue.BUILDKITE_JOB_ID),
            message = safeEnvValue(key = TestEnvironmentValue.BUILDKITE_MESSAGE)
        )
    }

    private fun getCircleCiEnvironment(): RunEnvironment? {
        val buildNumber = safeEnvValue(key = TestEnvironmentValue.CIRCLE_BUILD_NUM)
        val workflowId =
            buildNumber?.let { safeEnvValue(key = TestEnvironmentValue.CIRCLE_WORKFLOW_ID) }

        if (buildNumber == null || workflowId == null) return null

        return RunEnvironment(
            ci = "circleci",
            key = "$workflowId-$buildNumber",
            url = safeEnvValue(key = TestEnvironmentValue.CIRCLE_BUILD_URL),
            branch = safeEnvValue(key = TestEnvironmentValue.CIRCLE_BRANCH),
            commitSha = safeEnvValue(key = TestEnvironmentValue.CIRCLE_SHA1),
            number = buildNumber,
            message = "Build #$buildNumber on branch ${safeEnvValue(key = TestEnvironmentValue.CIRCLE_BRANCH) ?: "[Unknown branch]"}"
        )
    }

    private fun getGitHubActionsEnvironment(): RunEnvironment? {
        val action = safeEnvValue(key = TestEnvironmentValue.GITHUB_ACTION)
        val runNumber =
            action?.let { safeEnvValue(key = TestEnvironmentValue.GITHUB_RUN_NUMBER) }
        val runAttempt =
            runNumber?.let { safeEnvValue(key = TestEnvironmentValue.GITHUB_RUN_ATTEMPT) }

        if (action == null || runNumber == null || runAttempt == null) return null

        val repository = safeEnvValue(key = TestEnvironmentValue.GITHUB_REPOSITORY)
        val runId = safeEnvValue(key = TestEnvironmentValue.GITHUB_RUN_ID)
        val url = repository?.let { "https://github.com/$repository/actions/runs/$runId" }
        val workflowName = safeEnvValue(key = TestEnvironmentValue.GITHUB_WORKFLOW)
        val workflowStarter = safeEnvValue(key = TestEnvironmentValue.GITHUB_ACTOR)
        val message = buildString {
            append("Run #$runNumber attempt #$runAttempt")
            if (workflowName != null && workflowStarter != null) {
                append(" of $workflowName, started by $workflowStarter")
            }
        }

        return RunEnvironment(
            ci = "github_actions",
            key = "$action-$runNumber-$runAttempt",
            url = url,
            branch = safeEnvValue(key = TestEnvironmentValue.GITHUB_REF_NAME),
            commitSha = safeEnvValue(key = TestEnvironmentValue.GITHUB_SHA),
            number = runNumber,
            message = message
        )
    }

    private fun getLocalEnvironment(runKey: String): RunEnvironment {
        return RunEnvironment(
            ci = null,
            key = runKey
        )
    }
}
