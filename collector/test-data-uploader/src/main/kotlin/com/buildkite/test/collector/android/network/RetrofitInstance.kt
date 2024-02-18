package com.buildkite.test.collector.android.network

import com.buildkite.test.collector.android.util.CollectorUtils.Network.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitInstance {
    fun getRetrofitInstance(testSuiteApiToken: String): Retrofit {
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
