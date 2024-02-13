@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.buildkite.test.collector.android.sample"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments += mapOf(
            "listener" to "com.buildkite.test.collector.android.sample.MyTestCollector"
        )

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "String",
            "BUILDKITE_ANALYTICS_TOKEN",
            "\"${System.getenv("BUILDKITE_ANALYTICS_TOKEN")}\""
        )
        buildConfigField(
            "boolean",
            "BUILDKITE_ANALYTICS_DEBUG_ENABLED",
            System.getenv("BUILDKITE_ANALYTICS_DEBUG_ENABLED") ?: "false"
        )
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    androidTestImplementation(project(":collector:instrumented-test-collector"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeKtx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)

    testImplementation(libs.testing.junit)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test)

    androidTestImplementation(libs.androidx.compose.ui.tooling)
    androidTestImplementation(libs.androidx.compose.ui.testManifest)
}
