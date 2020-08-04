package com.and.newton.comms.network

import com.and.newton.comms.domain.data.Category
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SlackAPI {

    @POST("/api/chat.postMessage")
    suspend fun postToSlackChannel(@Query("token") token: String, @Query("channel") channel: String, @Query("text") text: String)

}