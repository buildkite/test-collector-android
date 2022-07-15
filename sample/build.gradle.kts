plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // TODO: Use below plugin to test the plugin published in local maven repository, Remove before publishing the repo and comment out before pushing changes
    id("com.buildkite.test-collector-android.unit-test-collector-plugin") version "0.1.0"
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.buildkite.sample"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments += mapOf(
            "listener" to "com.buildkite.sample.MyTestCollector"
        )

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "String",
            "BUILDKITE_ANALYTICS_TOKEN",
            "\"${System.getenv("BUILDKITE_ANALYTICS_TOKEN")}\""
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

    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.lifecycle)
    implementation(Dependencies.AndroidX.activity)
    implementation(Dependencies.AndroidX.Compose.material)
    implementation(Dependencies.AndroidX.Compose.UI.ui)
    implementation(Dependencies.AndroidX.Compose.UI.uiToolingPreview)

    testImplementation(Dependencies.Testing.jUnit)

    androidTestImplementation(project(":test-collector:instrumented-test-collector-library"))
    // TODO: Only use below implementation to test the library published in local maven repository, Remove before publishing the repo and comment out before pushing changes
    // androidTestImplementation("com.buildkite.test-collector-android:instrumented-test-collector:0.1.0")

    androidTestImplementation(Dependencies.AndroidX.Test.junit)
    androidTestImplementation(Dependencies.AndroidX.Test.espressoCore)
    androidTestImplementation(Dependencies.AndroidX.Compose.UI.Test.uiTestJunit)

    debugImplementation(Dependencies.AndroidX.Compose.UI.Debug.uiTooling)
    debugImplementation(Dependencies.AndroidX.Compose.UI.Debug.uiTestManifest)
}