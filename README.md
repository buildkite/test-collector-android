# Buildkite Collectors for Android (Beta)

Official [Buildkite Test Analytics](https://buildkite.com/test-analytics) collectors for Android ✨

📦 **Supported CI systems:** Buildkite, GitHub Actions, CircleCI, and others via
the `BUILDKITE_ANALYTICS_*` environment variables.

## 👉 Installing

### Step 1 - Retrieve API Token

[Create a test suite](https://buildkite.com/docs/test-analytics), and retrieve the API token. We'll
refer to this as `API Token` from here on.

### Step 2 - Environment Variable

Create an environment variable with the key `BUILDKITE_ANALYTICS_TOKEN` and value of
your `API Token`. This will need to be on your CI server, if running the BuildKite collector via CI,
or otherwise on your local machine.

### Step 3 - Unit Test Collector

In your app-level build.gradle.kts, add the following plugin:

```
plugins {
    id("com.buildkite.test-collector-android.unit-test-collector-plugin").version("0.1.0")
}
```

That's it!

### Step 4 - Instrumented Test Collector

In your app-level build.gradle.kts file,

Add the following dependency:

```
androidTestImplementation("com.buildkite.test-collector-android:instrumented-test-collector:0.1.0")
```

```
android {
    ...
    
    defaultConfig {
        ...
        
        buildConfigField(
            "String", 
            "BUILDKITE_ANALYTICS_TOKEN", 
            "\"${System.getenv("BUILDKITE_ANALYTICS_TOKEN")}\""
        )
    }
}    
```

Sync gradle, and rebuild the project to ensure the `BuildConfig` is generated.

Create the following class in your `androidTest` directory,
i.e. `src/androidTest/java/com/myapp/MyTestCollector.kt`

```
class MyTestCollector : InstrumentedTestCollector(
    apiToken = BuildConfig.BUILDKITE_ANALYTICS_TOKEN
)
```

Again, in your app-level build.gradle.kts file, instruct Gradle to use your test collector:

```
testInstrumentationRunnerArguments += mapOf(
    "listener" to "com.mycompany.myapp.MyTestCollector" // Make sure to use the correct package name here
)
```

Note: This test collector uploads test data via the device under test. Make sure your Android
device/emulator has network access.

## 🔍 Debugging

To enable debugging output, create and set `BUILDKITE_ANALYTICS_DEBUG_ENABLED` environment variable to `true` on your test environment (CI server or local machine).

For instrumented tests debugging, access the variable using `buildConfigField` and pass it through your `MyTestCollector` class. Refer the [example project](https://github.com/buildkite/test-collector-android/blob/main/example/) for implementation.

## ⚒ Developing

The Android test collector is separated into two parts - one for instrumented tests, and another for
unit tests.

Both parts upload tests via a common java library. See `collector/test-data-uploader`

#### Instrumented Tests

Instrumented tests run on an Android device, and gradle affords us the opportunity to
pass `testInstrumentationRunnerArguments` to the test runner. We take advantage of this to pass our
own custom test listener. This listener observes test runs, and calls our test uploader to publish
results.

This listener is provided to the user as an Android library.
See `collector/instrumented-test-collector`

At minimum, the test listener requires an API token in order to publish results to Buildkite's
servers.

Since the 'environment' is the app's process / Android device, we can't simply
call `System.getEnv()` to reach environment variables. Instead, we have to pass env vars through
from the CI/local machine, to the app's process, via `BuildConfig`. And, since the env vars live on
the end-user's CI/local machine, we have to ask them to generate the `BuildConfig` from within their
own app's `build.gradle`, and then pass the config to their own subclass of the test listener. They
then instruct gradle to use this test listener, which now has access to their `BuildConfig`, and
most importantly, their API token.

#### Unit Tests

There's no equivalent `testInstrumentationRunnerArguments` flag for unit tests - these run on the
CI/local machine, rather than the Android device. JUnit doesn't seem to offer any way to set a
global test runner / test listener - instead, we'd need to ask the user to annotate each unit test
with `@RunWith(OurTestCollector::class)`. This is rather onerous for the end user. So, instead of
using a JUnit test listener, we hook into the Gradle testing APIs, and add a test listener via
Gradle.

This listener is provided to the user as a Gradle plugin.
See `collector/unit-test-collector`

Note that there is no equivalent gradle API for instrumented tests, hence the two separate
approaches.

Fortunately, since unit tests run on the CI/local machine, our Gradle plugin has direct access to
environment variables, so no additional config is required.

#### Access Snapshots

To access and test the published snapshots, add the below repository configuration inside `repositories` block, and append the dependency versions with `-SNAPSHOT`:

```
maven {
    url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}
```

The snapshots can be found [here](https://s01.oss.sonatype.org/content/repositories/snapshots/com/buildkite/).

---

Useful resources for developing collectors include
the [Buildkite Test Analytics docs](https://buildkite.com/docs/test-analytics).

## 👩‍💻 Contributing

Please refer the contribution guide [here](CONTRIBUTION.md). 
Bug reports and pull requests are welcome on GitHub at https://github.com/buildkite/test-collector-android

## 📜 License

See [LICENSE.MD](/LICENSE.MD)