import com.android.build.api.dsl.ApplicationDefaultConfig

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.buildkite.test.collector.android.example"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Specifies `InstrumentedTestCollector` as the instrumented test listener for collecting test analytics.
        testInstrumentationRunnerArguments["listener"] =
            "com.buildkite.test.collector.android.InstrumentedTestCollector"

        setupTestCollectorEnvironment()

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.buildkite.test.collector.android.example"
}

dependencies {
    androidTestImplementation(projects.collector.instrumentedTestCollector)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeKtx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)

    testImplementation(libs.testing.junit)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.compose.ui.test)
}

fun ApplicationDefaultConfig.setupTestCollectorEnvironment() {
    // Passes environment variables as instrumentation arguments
    testInstrumentationRunnerArguments["BUILDKITE_ANALYTICS_TOKEN"] =
        getEnv("BUILDKITE_ANALYTICS_TOKEN")
    testInstrumentationRunnerArguments["BUILDKITE_ANALYTICS_DEBUG_ENABLED"] =
        getEnv("BUILDKITE_ANALYTICS_DEBUG_ENABLED")
    testInstrumentationRunnerArguments["GITHUB_ACTION"] =
        getEnv("GITHUB_ACTION")
    testInstrumentationRunnerArguments["GITHUB_RUN_ID"] =
        getEnv("GITHUB_RUN_ID")
    testInstrumentationRunnerArguments["GITHUB_RUN_NUMBER"] =
        getEnv("GITHUB_RUN_NUMBER")
    testInstrumentationRunnerArguments["GITHUB_RUN_ATTEMPT"] =
        getEnv("GITHUB_RUN_ATTEMPT")
    testInstrumentationRunnerArguments["GITHUB_REF_NAME"] =
        getEnv("GITHUB_REF_NAME")
    testInstrumentationRunnerArguments["GITHUB_REPOSITORY"] =
        getEnv("GITHUB_REPOSITORY")
    testInstrumentationRunnerArguments["GITHUB_SHA"] =
        getEnv("GITHUB_SHA")
    testInstrumentationRunnerArguments["GITHUB_WORKFLOW"] =
        getEnv("GITHUB_WORKFLOW")
    testInstrumentationRunnerArguments["GITHUB_ACTOR"] =
        getEnv("GITHUB_ACTOR")
}

fun getEnv(key: String): String = System.getenv(key) ?: ""
