package com.buildkite.test.collector.android.tracer.environment

/**
 * Contains environment variable keys for configuring the Buildkite test analytics collector.
 *
 * These constants are used to retrieve environment values from local machines or Continuous Integration (CI) environments
 * to configure the Buildkite test analytics collector for uploading test data.
 */
object TestEnvironmentValue {
    const val BUILDKITE_ANALYTICS_TOKEN = "BUILDKITE_ANALYTICS_TOKEN"
    const val BUILDKITE_ANALYTICS_DEBUG_ENABLED = "BUILDKITE_ANALYTICS_DEBUG_ENABLED"
    const val BUILDKITE_ANALYTICS_TAGS = "BUILDKITE_ANALYTICS_TAGS"

    const val BUILDKITE_BRANCH = "BUILDKITE_BRANCH"
    const val BUILDKITE_BUILD_ID = "BUILDKITE_BUILD_ID"
    const val BUILDKITE_BUILD_NUMBER = "BUILDKITE_BUILD_NUMBER"
    const val BUILDKITE_BUILD_URL = "BUILDKITE_BUILD_URL"
    const val BUILDKITE_COMMIT = "BUILDKITE_COMMIT"
    const val BUILDKITE_JOB_ID = "BUILDKITE_JOB_ID"
    const val BUILDKITE_MESSAGE = "BUILDKITE_MESSAGE"

    const val CIRCLE_BRANCH = "CIRCLE_BRANCH"
    const val CIRCLE_BUILD_NUM = "CIRCLE_BUILD_NUM"
    const val CIRCLE_BUILD_URL = "CIRCLE_BUILD_URL"
    const val CIRCLE_SHA1 = "CIRCLE_SHA1"
    const val CIRCLE_WORKFLOW_ID = "CIRCLE_WORKFLOW_ID"

    const val GITHUB_ACTION = "GITHUB_ACTION"
    const val GITHUB_RUN_ID = "GITHUB_RUN_ID"
    const val GITHUB_RUN_NUMBER = "GITHUB_RUN_NUMBER"
    const val GITHUB_RUN_ATTEMPT = "GITHUB_RUN_ATTEMPT"
    const val GITHUB_REF_NAME = "GITHUB_REF_NAME"
    const val GITHUB_REPOSITORY = "GITHUB_REPOSITORY"
    const val GITHUB_SHA = "GITHUB_SHA"
    const val GITHUB_WORKFLOW = "GITHUB_WORKFLOW"
    const val GITHUB_ACTOR = "GITHUB_ACTOR"
}
