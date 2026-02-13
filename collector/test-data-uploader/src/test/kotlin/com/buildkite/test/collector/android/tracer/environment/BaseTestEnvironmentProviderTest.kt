package com.buildkite.test.collector.android.tracer.environment

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

// Mock implementation of BaseTestEnvironmentProvider for testing purposes
class MockTestEnvironmentProvider(
    private val environment: Map<String, String?>
) : BaseTestEnvironmentProvider() {
    override fun getEnvironmentValue(key: String): String? = environment[key]
}

class BaseTestEnvironmentProviderTest {

    @Test
    fun testNoEnvironmentValues() {
        val noEnvironmentValues = emptyMap<String, String?>()
        val testProvider = MockTestEnvironmentProvider(environment = noEnvironmentValues)

        assertNull(testProvider.testSuiteApiToken)
        assertFalse(testProvider.isDebugEnabled)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertNull(environment.ci)
        assertEquals(testDefaultKey, environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertNull(environment.number)
        assertNull(environment.jobId)
        assertNull(environment.message)
    }

    @Test
    fun testLocalEnvironmentWithValues() {
        val localEnvironmentWithValues = mapOf(
            TestEnvironmentValue.BUILDKITE_ANALYTICS_TOKEN to "test-token",
            TestEnvironmentValue.BUILDKITE_ANALYTICS_DEBUG_ENABLED to "true"
        )
        val testProvider = MockTestEnvironmentProvider(environment = localEnvironmentWithValues)

        assertEquals("test-token", testProvider.testSuiteApiToken)
        assertTrue(testProvider.isDebugEnabled)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertNull(environment.ci)
        assertEquals(testDefaultKey, environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertNull(environment.number)
        assertNull(environment.jobId)
        assertNull(environment.message)
    }

    @Test
    fun testLocalEnvironmentWithInvalidValues() {
        val invalidEnvironmentValues = mapOf(
            TestEnvironmentValue.BUILDKITE_ANALYTICS_TOKEN to "null",
            TestEnvironmentValue.BUILDKITE_ANALYTICS_DEBUG_ENABLED to "invalid-boolean-value"
        )
        val testProvider = MockTestEnvironmentProvider(environment = invalidEnvironmentValues)

        assertNull(testProvider.testSuiteApiToken)
        assertFalse(testProvider.isDebugEnabled)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertNull(environment.ci)
        assertEquals(testDefaultKey, environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertNull(environment.number)
        assertNull(environment.jobId)
        assertNull(environment.message)
    }

    @Test
    fun testBuildkiteCIEnvironmentWithAllValues() {
        val buildkiteEnvironmentWithAllValues = mapOf(
            TestEnvironmentValue.BUILDKITE_BUILD_ID to "build-id",
            TestEnvironmentValue.BUILDKITE_BUILD_URL to "http://buildkite.com/build",
            TestEnvironmentValue.BUILDKITE_BRANCH to "main",
            TestEnvironmentValue.BUILDKITE_COMMIT to "commit-sha",
            TestEnvironmentValue.BUILDKITE_BUILD_NUMBER to "42",
            TestEnvironmentValue.BUILDKITE_JOB_ID to "job-id",
            TestEnvironmentValue.BUILDKITE_MESSAGE to "Build message"
        )
        val testProvider =
            MockTestEnvironmentProvider(environment = buildkiteEnvironmentWithAllValues)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertEquals("buildkite", environment.ci)
        assertEquals("build-id", environment.key)
        assertEquals("http://buildkite.com/build", environment.url)
        assertEquals("main", environment.branch)
        assertEquals("commit-sha", environment.commitSha)
        assertEquals("42", environment.number)
        assertEquals("job-id", environment.jobId)
        assertEquals("Build message", environment.message)
    }

    @Test
    fun testBuildkiteCIEnvironmentWithMissingValues() {
        val buildkiteEnvironmentWithMissingValues = mapOf(
            TestEnvironmentValue.BUILDKITE_BUILD_ID to "build-id"
        )
        val testProvider =
            MockTestEnvironmentProvider(environment = buildkiteEnvironmentWithMissingValues)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertEquals("buildkite", environment.ci)
        assertEquals("build-id", environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertNull(environment.number)
        assertNull(environment.jobId)
        assertNull(environment.message)
    }

    @Test
    fun testBuildkiteCIEnvironmentWithInvalidValues() {
        val buildkiteEnvironmentWithInvalidValues = mapOf(
            TestEnvironmentValue.BUILDKITE_BUILD_ID to "null",
            TestEnvironmentValue.BUILDKITE_BUILD_URL to "   ",
            TestEnvironmentValue.BUILDKITE_BRANCH to "null",
            TestEnvironmentValue.BUILDKITE_COMMIT to "   ",
            TestEnvironmentValue.BUILDKITE_BUILD_NUMBER to "null",
            TestEnvironmentValue.BUILDKITE_JOB_ID to "null",
            TestEnvironmentValue.BUILDKITE_MESSAGE to "null"
        )
        val testProvider =
            MockTestEnvironmentProvider(environment = buildkiteEnvironmentWithInvalidValues)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertNull(environment.ci)
        assertEquals(testDefaultKey, environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertNull(environment.number)
        assertNull(environment.jobId)
        assertNull(environment.message)
    }

    @Test
    fun testCircleCiEnvironmentWithAllValues() {
        val circleCiEnvironmentWithAllValues = mapOf(
            TestEnvironmentValue.CIRCLE_BUILD_NUM to "123",
            TestEnvironmentValue.CIRCLE_WORKFLOW_ID to "workflow-id",
            TestEnvironmentValue.CIRCLE_BUILD_URL to "http://circleci.com/build",
            TestEnvironmentValue.CIRCLE_BRANCH to "main",
            TestEnvironmentValue.CIRCLE_SHA1 to "commit-sha"
        )
        val testProvider = MockTestEnvironmentProvider(environment = circleCiEnvironmentWithAllValues)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertEquals("circleci", environment.ci)
        assertEquals("workflow-id-123", environment.key)
        assertEquals("http://circleci.com/build", environment.url)
        assertEquals("main", environment.branch)
        assertEquals("commit-sha", environment.commitSha)
        assertEquals("123", environment.number)
        assertEquals("Build #123 on branch main", environment.message)
    }

    @Test
    fun testCircleCiEnvironmentWithMissingBuildNumberKey() {
        val circleCiEnvironmentWithMissingBuildNumber = mapOf(
            TestEnvironmentValue.CIRCLE_WORKFLOW_ID to "workflow-id",
            TestEnvironmentValue.CIRCLE_BUILD_URL to "http://circleci.com/build",
            TestEnvironmentValue.CIRCLE_BRANCH to "main",
            TestEnvironmentValue.CIRCLE_SHA1 to "commit-sha"
        )
        val testProvider = MockTestEnvironmentProvider(environment = circleCiEnvironmentWithMissingBuildNumber)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertNull(environment.ci)
        assertEquals(testDefaultKey, environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertNull(environment.number)
        assertNull(environment.message)
    }

    @Test
    fun testCircleCiEnvironmentWithMissingWorkflowIdKey() {
        val circleCiEnvironmentWithMissingWorkflowId = mapOf(
            TestEnvironmentValue.CIRCLE_BUILD_NUM to "123",
            TestEnvironmentValue.CIRCLE_BUILD_URL to "http://circleci.com/build",
            TestEnvironmentValue.CIRCLE_BRANCH to "main",
            TestEnvironmentValue.CIRCLE_SHA1 to "commit-sha"
        )
        val testProvider = MockTestEnvironmentProvider(environment = circleCiEnvironmentWithMissingWorkflowId)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertNull(environment.ci)
        assertEquals(testDefaultKey, environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertNull(environment.number)
        assertNull(environment.message)
    }

    @Test
    fun testCircleCiEnvironmentWithMissingValues() {
        val circleCiEnvironmentWithMissingValues = mapOf(
            TestEnvironmentValue.CIRCLE_BUILD_NUM to "123",
            TestEnvironmentValue.CIRCLE_WORKFLOW_ID to "workflow-id"
        )
        val testProvider = MockTestEnvironmentProvider(environment = circleCiEnvironmentWithMissingValues)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertEquals("circleci", environment.ci)
        assertEquals("workflow-id-123", environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertEquals("123", environment.number)
        assertEquals("Build #123 on branch [Unknown branch]", environment.message)
    }

    @Test
    fun testCircleCiEnvironmentWithInvalidValues() {
        val circleCiEnvironmentWithInvalidValues = mapOf(
            TestEnvironmentValue.CIRCLE_BUILD_NUM to "null",
            TestEnvironmentValue.CIRCLE_WORKFLOW_ID to "   ",
            TestEnvironmentValue.CIRCLE_BUILD_URL to "null",
            TestEnvironmentValue.CIRCLE_BRANCH to "   ",
            TestEnvironmentValue.CIRCLE_SHA1 to "null"
        )
        val testProvider = MockTestEnvironmentProvider(environment = circleCiEnvironmentWithInvalidValues)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertNull(environment.ci)
        assertEquals(testDefaultKey, environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertNull(environment.number)
        assertNull(environment.message)
    }

    @Test
    fun testGitHubActionsEnvironmentWithAllValues() {
        val gitHubActionsEnvironmentWithAllValues = mapOf(
            TestEnvironmentValue.GITHUB_ACTION to "action",
            TestEnvironmentValue.GITHUB_RUN_NUMBER to "1",
            TestEnvironmentValue.GITHUB_RUN_ATTEMPT to "1",
            TestEnvironmentValue.GITHUB_REPOSITORY to "repository",
            TestEnvironmentValue.GITHUB_RUN_ID to "run-id",
            TestEnvironmentValue.GITHUB_WORKFLOW to "workflow",
            TestEnvironmentValue.GITHUB_ACTOR to "actor",
            TestEnvironmentValue.GITHUB_REF_NAME to "branch",
            TestEnvironmentValue.GITHUB_SHA to "commit-sha"
        )
        val testProvider = MockTestEnvironmentProvider(environment = gitHubActionsEnvironmentWithAllValues)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertEquals("github_actions", environment.ci)
        assertEquals("action-1-1", environment.key)
        assertEquals("https://github.com/repository/actions/runs/run-id", environment.url)
        assertEquals("branch", environment.branch)
        assertEquals("commit-sha", environment.commitSha)
        assertEquals("1", environment.number)
        assertEquals("Run #1 attempt #1 of workflow, started by actor", environment.message)
    }

    @Test
    fun testGitHubActionsEnvironmentWithMissingActionKey() {
        val gitHubActionsEnvironmentWithMissingAction = mapOf(
            TestEnvironmentValue.GITHUB_RUN_NUMBER to "1",
            TestEnvironmentValue.GITHUB_RUN_ATTEMPT to "1",
            TestEnvironmentValue.GITHUB_REPOSITORY to "repository",
            TestEnvironmentValue.GITHUB_RUN_ID to "run-id",
            TestEnvironmentValue.GITHUB_WORKFLOW to "workflow",
            TestEnvironmentValue.GITHUB_ACTOR to "actor",
            TestEnvironmentValue.GITHUB_REF_NAME to "branch",
            TestEnvironmentValue.GITHUB_SHA to "commit-sha"
        )
        val testProvider = MockTestEnvironmentProvider(environment = gitHubActionsEnvironmentWithMissingAction)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertNull(environment.ci)
        assertEquals(testDefaultKey, environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertNull(environment.number)
        assertNull(environment.message)
    }

    @Test
    fun testGitHubActionsEnvironmentWithMissingRunNumberKey() {
        val gitHubActionsEnvironmentWithMissingRunNumber = mapOf(
            TestEnvironmentValue.GITHUB_ACTION to "action",
            TestEnvironmentValue.GITHUB_RUN_ATTEMPT to "1",
            TestEnvironmentValue.GITHUB_REPOSITORY to "repository",
            TestEnvironmentValue.GITHUB_RUN_ID to "run-id",
            TestEnvironmentValue.GITHUB_WORKFLOW to "workflow",
            TestEnvironmentValue.GITHUB_ACTOR to "actor",
            TestEnvironmentValue.GITHUB_REF_NAME to "branch",
            TestEnvironmentValue.GITHUB_SHA to "commit-sha"
        )
        val testProvider = MockTestEnvironmentProvider(environment = gitHubActionsEnvironmentWithMissingRunNumber)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertNull(environment.ci)
        assertEquals(testDefaultKey, environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertNull(environment.number)
        assertNull(environment.message)
    }

    @Test
    fun testGitHubActionsEnvironmentWithMissingRunAttemptKey() {
        val gitHubActionsEnvironmentWithMissingRunAttempt = mapOf(
            TestEnvironmentValue.GITHUB_ACTION to "action",
            TestEnvironmentValue.GITHUB_RUN_NUMBER to "1",
            TestEnvironmentValue.GITHUB_REPOSITORY to "repository",
            TestEnvironmentValue.GITHUB_RUN_ID to "run-id",
            TestEnvironmentValue.GITHUB_WORKFLOW to "workflow",
            TestEnvironmentValue.GITHUB_ACTOR to "actor",
            TestEnvironmentValue.GITHUB_REF_NAME to "branch",
            TestEnvironmentValue.GITHUB_SHA to "commit-sha"
        )
        val testProvider = MockTestEnvironmentProvider(environment = gitHubActionsEnvironmentWithMissingRunAttempt)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertNull(environment.ci)
        assertEquals(testDefaultKey, environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertNull(environment.number)
        assertNull(environment.message)
    }

    @Test
    fun testGitHubActionsEnvironmentWithMissingValues() {
        val gitHubActionsEnvironmentWithMissingValues = mapOf(
            TestEnvironmentValue.GITHUB_ACTION to "action",
            TestEnvironmentValue.GITHUB_RUN_NUMBER to "1",
            TestEnvironmentValue.GITHUB_RUN_ATTEMPT to "1"
        )
        val testProvider = MockTestEnvironmentProvider(environment = gitHubActionsEnvironmentWithMissingValues)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertEquals("github_actions", environment.ci)
        assertEquals("action-1-1", environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertEquals("1", environment.number)
        assertEquals("Run #1 attempt #1", environment.message)
    }

    @Test
    fun testGitHubActionsEnvironmentWithInvalidValues() {
        val gitHubActionsEnvironmentWithInvalidValues = mapOf(
            TestEnvironmentValue.GITHUB_ACTION to "null",
            TestEnvironmentValue.GITHUB_RUN_NUMBER to "   ",
            TestEnvironmentValue.GITHUB_RUN_ATTEMPT to "null",
            TestEnvironmentValue.GITHUB_REPOSITORY to "null",
            TestEnvironmentValue.GITHUB_RUN_ID to "null",
            TestEnvironmentValue.GITHUB_WORKFLOW to "null",
            TestEnvironmentValue.GITHUB_ACTOR to "null",
            TestEnvironmentValue.GITHUB_REF_NAME to "   ",
            TestEnvironmentValue.GITHUB_SHA to "null"
        )
        val testProvider = MockTestEnvironmentProvider(environment = gitHubActionsEnvironmentWithInvalidValues)

        val environment = testProvider.getRunEnvironment(testDefaultKey)

        assertNull(environment.ci)
        assertEquals(testDefaultKey, environment.key)
        assertNull(environment.url)
        assertNull(environment.branch)
        assertNull(environment.commitSha)
        assertNull(environment.number)
        assertNull(environment.message)
    }

    @Test
    fun testUploadTagsWithValidJson() {
        val environment = mapOf(
            TestEnvironmentValue.BUILDKITE_ANALYTICS_TAGS to """{"environment":"production","language.name":"kotlin"}"""
        )
        val testProvider = MockTestEnvironmentProvider(environment = environment)

        val tags = testProvider.uploadTags
        assertEquals(2, tags.size)
        assertEquals("production", tags["environment"])
        assertEquals("kotlin", tags["language.name"])
    }

    @Test
    fun testUploadTagsWithInvalidJson() {
        val environment = mapOf(
            TestEnvironmentValue.BUILDKITE_ANALYTICS_TAGS to "not valid json"
        )
        val testProvider = MockTestEnvironmentProvider(environment = environment)

        assertTrue(testProvider.uploadTags.isEmpty())
    }

    @Test
    fun testUploadTagsWithMissingEnvVar() {
        val testProvider = MockTestEnvironmentProvider(environment = emptyMap())

        assertTrue(testProvider.uploadTags.isEmpty())
    }

    @Test
    fun testUploadTagsWithBlankValue() {
        val environment = mapOf(
            TestEnvironmentValue.BUILDKITE_ANALYTICS_TAGS to "   "
        )
        val testProvider = MockTestEnvironmentProvider(environment = environment)

        assertTrue(testProvider.uploadTags.isEmpty())
    }

    @Test
    fun testUploadTagsWithEmptyJsonObject() {
        val environment = mapOf(
            TestEnvironmentValue.BUILDKITE_ANALYTICS_TAGS to "{}"
        )
        val testProvider = MockTestEnvironmentProvider(environment = environment)

        assertTrue(testProvider.uploadTags.isEmpty())
    }
}

private const val testDefaultKey = "test-default-run-key"
