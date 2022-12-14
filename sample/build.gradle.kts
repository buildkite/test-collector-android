plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.buildkite.test-collector-android.unit-test-collector-plugin")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.buildkite.test.collector.android.sample"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

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
        kotlinCompilerExtensionVersion = Dependencies.AndroidX.Compose.version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    androidTestImplementation(project(":collector:instrumented-test-collector"))

    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.lifecycle)
    implementation(Dependencies.AndroidX.activity)
    implementation(Dependencies.AndroidX.Compose.material)
    implementation(Dependencies.AndroidX.Compose.UI.ui)
    implementation(Dependencies.AndroidX.Compose.UI.uiToolingPreview)

    testImplementation(Dependencies.Testing.jUnit)

    androidTestImplementation(Dependencies.AndroidX.Test.junit)
    androidTestImplementation(Dependencies.AndroidX.Test.espressoCore)
    androidTestImplementation(Dependencies.AndroidX.Compose.UI.Test.uiTestJunit)

    debugImplementation(Dependencies.AndroidX.Compose.UI.Debug.uiTooling)
    debugImplementation(Dependencies.AndroidX.Compose.UI.Debug.uiTestManifest)
}
