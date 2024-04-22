package com.buildkite.test.collector.android.network

import com.buildkite.test.collector.android.util.CollectorUtils.Network.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object TestAnalyticsRetrofit {

    /**
     * Creates a Retrofit instance specifically configured for communicating with the Test Analytics API.
     * Adds an [Interceptor] to append an authorization header containing the provided API token to all outgoing requests.
     *
     * @param testSuiteApiToken The test suite's API token to authenticate with Test Analytics.
     */
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
