plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.buildkite.test-collector-android.unit-test-collector-plugin")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.buildkite.example_project"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false // IMPORTANT BIT else you release aar will have no classes
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
    
    implementation(project(":test-collector:instrumented-test-collector-library"))
    
    // Comment out the above line and uncomment the below implementation to test the library published on local maven repository
    // Note: Run './gradlew publishToMavenLocal' command if the library isn't published locally
//     implementation("com.buildkite.test-collector-android:instrumented-test-collector:0.1.0")
    
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