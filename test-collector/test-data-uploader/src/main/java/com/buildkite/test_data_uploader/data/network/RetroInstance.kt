package com.buildkite.test_data_uploader.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {

        private const val BASE_URL = "https://analytics-api.buildkite.com/v1/"

        fun getRetroInstance(testSuiteApiToken: String): Retrofit {
            val client = OkHttpClient.Builder()
            client.addInterceptor(
                    Interceptor { chain ->
                        chain.proceed(
                            chain.request().newBuilder()
                                .addHeader("Authorization", "Token token=\"$testSuiteApiToken\"")
                                .build()
                        )
                    }
                )

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
