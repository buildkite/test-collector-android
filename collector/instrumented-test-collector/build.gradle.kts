plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.maven.publish)
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 16

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    namespace = "com.buildkite.test.collector.android"
}

dependencies {
    implementation(projects.collector.testDataUploader)
    implementation(libs.testing.junit)
    implementation(libs.androidx.monitor)
}
