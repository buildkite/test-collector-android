object Dependencies {

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.8.0"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
        const val activity = "androidx.activity:activity-compose:1.4.0"
        const val appcompat = "androidx.appcompat:appcompat:1.4.2"

        object Compose {
            const val version = "1.2.0-beta03"

            const val material = "androidx.compose.material:material:$version"

            object UI {
                const val ui = "androidx.compose.ui:ui:$version"
                const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"

                object Test {
                    const val uiTestJunit = "androidx.compose.ui:ui-test-junit4:$version"
                }

                object Debug {
                    const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
                    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
                }
            }
        }

        object Test {
            const val junit = "androidx.test.ext:junit:1.1.3"
            const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
        }
    }

    object Squareup {
        object Retrofit2 {
            const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
            const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
        }

        object Okhttp3 {
            const val okhttp = "com.squareup.okhttp3:okhttp:5.0.0-alpha.2"
            const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2"
        }
    }

    object Google {
        object Android {
            const val material = "com.google.android.material:material:1.6.1"
        }

        object Code {
            const val gson = "com.google.code.gson:gson:2.9.0"
        }
    }

    object Testing {
        const val jUnit = "junit:junit:4.13.2"
    }
}
