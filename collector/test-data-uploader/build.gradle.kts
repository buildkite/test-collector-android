plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.vanniktech.maven.publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Dependencies.Squareup.Retrofit2.retrofit)
    implementation(Dependencies.Squareup.Retrofit2.converterGson)
    implementation(Dependencies.Squareup.Okhttp3.okhttp)
    implementation(Dependencies.Squareup.Okhttp3.loggingInterceptor)
    implementation(Dependencies.Google.Code.gson)
}
