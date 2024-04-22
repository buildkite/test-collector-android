package com.buildkite.test.collector.android.network.api

import com.buildkite.test.collector.android.model.TestData
import com.buildkite.test.collector.android.model.TestUploadResponse
import com.buildkite.test.collector.android.util.CollectorUtils.Network.TEST_UPLOADER_ENDPOINT
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface TestUploaderApi {

    @POST(TEST_UPLOADER_ENDPOINT)
    @Headers(
        "Content-Type:application/json"
    )
    fun uploadTestData(@Body testData: TestData): Call<TestUploadResponse>
}
