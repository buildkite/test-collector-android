package com.buildkite.test_data_uploader.data.network

import com.buildkite.test_data_uploader.domain.model.api.TestData
import com.buildkite.test_data_uploader.domain.model.api.TestResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TestAnalyticsApi {

    @POST("uploads")
    @Headers(
        "Content-Type:application/json"
    )
    fun uploadTestData(@Body testData: TestData): Call<TestResponse>
}
