package com.buildkite.test_data_uploader.data.network

import com.buildkite.test_data_uploader.models.TestData
import com.buildkite.test_data_uploader.models.TestResponse
import com.buildkite.test_data_uploader.util.Constants.Network.TEST_UPLOADER_ENDPOINT
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TestUploaderApi {

    @POST(TEST_UPLOADER_ENDPOINT)
    @Headers(
        "Content-Type:application/json"
    )
    fun uploadTestData(@Body testData: TestData): Call<TestResponse>
}
