# CI Environment Variables Setup

This document provides instructions for setting up environment variables for different CI platforms to enrich test reports.

## Accessing Environment Variables in CI Configuration

To enrich your test reports with valuable CI information such as commit messages, branch names, and build numbers, you need to pass environment variables in your CI pipeline.
For example, here is how you can set up the environment variables for GitHub Actions:

### GitHub Actions

In your GitHub Actions workflow configuration, add the following environment variables:

```yaml
env:
  BUILDKITE_ANALYTICS_TOKEN: ${{ secrets.BUILDKITE_ANALYTICS_TOKEN }}
  GITHUB_ACTION: ${{ github.action }}
  GITHUB_RUN_ID: ${{ github.run_id }}
  GITHUB_RUN_NUMBER: ${{ github.run_number }}
  GITHUB_RUN_ATTEMPT: ${{ github.run_attempt }}
  GITHUB_REPOSITORY: ${{ github.repository }}
  GITHUB_REF_NAME: ${{ github.ref_name }}
  GITHUB_SHA: ${{ github.sha }}
  GITHUB_WORKFLOW: ${{ github.workflow }}
  GITHUB_ACTOR: ${{ github.actor }}
```

## Additional Setup for Instrumented Tests

While the above setup is sufficient for unit tests collector, instrumented tests collector require additional configuration. Below are examples for different CI platforms supported by the test collectors.

In your build.gradle.kts file, add the following to pass the required environment variables for instrumented tests:

### Buildkite

```
android {
    ...
    defaultConfig {
        ...
        testInstrumentationRunnerArguments["BUILDKITE_ANALYTICS_TOKEN"] = System.getenv("BUILDKITE_ANALYTICS_TOKEN")
        testInstrumentationRunnerArguments["BUILDKITE_BUILD_ID"] = System.getenv("BUILDKITE_BUILD_ID") ?: ""
        testInstrumentationRunnerArguments["BUILDKITE_BUILD_URL"] = System.getenv("BUILDKITE_BUILD_URL") ?: ""
        testInstrumentationRunnerArguments["BUILDKITE_BRANCH"] = System.getenv("BUILDKITE_BRANCH") ?: ""
        testInstrumentationRunnerArguments["BUILDKITE_COMMIT"] = System.getenv("BUILDKITE_COMMIT") ?: ""
        testInstrumentationRunnerArguments["BUILDKITE_BUILD_NUMBER"] = System.getenv("BUILDKITE_BUILD_NUMBER") ?: ""
        testInstrumentationRunnerArguments["BUILDKITE_JOB_ID"] = System.getenv("BUILDKITE_JOB_ID") ?: ""
        testInstrumentationRunnerArguments["BUILDKITE_MESSAGE"] = System.getenv("BUILDKITE_MESSAGE") ?: ""
    }
}
```

### GitHub Actions

```
android {
    ...
    defaultConfig {
        ...
        testInstrumentationRunnerArguments["BUILDKITE_ANALYTICS_TOKEN"] = System.getenv("BUILDKITE_ANALYTICS_TOKEN")
        testInstrumentationRunnerArguments["GITHUB_ACTION"] = System.getenv("GITHUB_ACTION") ?: ""
        testInstrumentationRunnerArguments["GITHUB_RUN_ID"] = System.getenv("GITHUB_RUN_ID") ?: ""
        testInstrumentationRunnerArguments["GITHUB_RUN_NUMBER"] = System.getenv("GITHUB_RUN_NUMBER") ?: ""
        testInstrumentationRunnerArguments["GITHUB_RUN_ATTEMPT"] = System.getenv("GITHUB_RUN_ATTEMPT") ?: ""
        testInstrumentationRunnerArguments["GITHUB_REPOSITORY"] = System.getenv("GITHUB_REPOSITORY") ?: ""
        testInstrumentationRunnerArguments["GITHUB_REF_NAME"] = System.getenv("GITHUB_REF_NAME") ?: ""
        testInstrumentationRunnerArguments["GITHUB_SHA"] = System.getenv("GITHUB_SHA") ?: ""
        testInstrumentationRunnerArguments["GITHUB_WORKFLOW"] = System.getenv("GITHUB_WORKFLOW") ?: ""
        testInstrumentationRunnerArguments["GITHUB_ACTOR"] = System.getenv("GITHUB_ACTOR") ?: ""
    }
}
```

### CircleCI

```
android {
    ...
    defaultConfig {
        ...
        testInstrumentationRunnerArguments["BUILDKITE_ANALYTICS_TOKEN"] = System.getenv("BUILDKITE_ANALYTICS_TOKEN")
        testInstrumentationRunnerArguments["CIRCLE_BUILD_NUM"] = System.getenv("CIRCLE_BUILD_NUM") ?: ""
        testInstrumentationRunnerArguments["CIRCLE_WORKFLOW_ID"] = System.getenv("CIRCLE_WORKFLOW_ID") ?: ""
        testInstrumentationRunnerArguments["CIRCLE_BUILD_URL"] = System.getenv("CIRCLE_BUILD_URL") ?: ""
        testInstrumentationRunnerArguments["CIRCLE_BRANCH"] = System.getenv("CIRCLE_BRANCH") ?: ""
        testInstrumentationRunnerArguments["CIRCLE_SHA1"] = System.getenv("CIRCLE_SHA1") ?: ""
    }
}
```

By following these steps, you ensure that your CI environment variables are passed correctly to your Android instrumentation and unit tests. 
For a complete setup example, check out the [example](example) project in the repository.