# AGENTS.md — test-collector-android

Android test collector for [Buildkite Test Engine](https://buildkite.com/docs/test-engine/test-collection/android-collectors). Collects test results and uploads them to the Test Analytics API.

## Build & Test (requires JDK 17)
- **Build all**: `./gradlew build`
- **Unit tests**: `./gradlew :collector:test-data-uploader:test`
- **Single test**: `./gradlew :collector:test-data-uploader:test --tests "com.buildkite.test.collector.android.models.RunEnvironmentTest"`
- **Lint**: `support/scripts/lint` (auto-fix: `support/scripts/lint -F`)
- **Publish locally**: `./gradlew publishToMavenLocal`

## Architecture
Three modules under `collector/`:
- **test-data-uploader** — Pure Kotlin/JVM library: models (`TestData`, `RunEnvironment`), network layer (Retrofit/OkHttp → Buildkite Test Analytics API), tracing (`TestObserver`). This is the shared core.
- **unit-test-collector** — Gradle plugin (`UnitTestCollectorPlugin`) that hooks into Gradle's test listener API to collect unit test results. Depends on test-data-uploader.
- **instrumented-test-collector** — Android library providing a JUnit `RunListener` (`InstrumentedTestCollector`) for on-device instrumented tests. Depends on test-data-uploader.
- **example/** — Sample Android app for manual testing.

## Code Style & Conventions
- Kotlin with `kotlin.code.style=official`. Package: `com.buildkite.test.collector.android`.
- Java 17 target for JVM modules; Java 8 target for the Android library module.
- Detekt for static analysis (config: `support/vendor/detekt/detekt.yml`).
- When bumping version, update both `gradle.properties` `VERSION_NAME` and `RunEnvironment.VERSION_NAME`.
- Environment variable for API token: `BUILDKITE_ANALYTICS_TOKEN`. Debug mode: `BUILDKITE_ANALYTICS_DEBUG_ENABLED=true`.
