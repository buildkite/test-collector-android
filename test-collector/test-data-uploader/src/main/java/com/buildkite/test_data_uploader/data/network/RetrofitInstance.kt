package com.buildkite.test_data_uploader.data.network

import com.buildkite.test_data_uploader.util.Constants.Network.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
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
}
