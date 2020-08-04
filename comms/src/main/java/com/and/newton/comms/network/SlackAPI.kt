package com.and.newton.comms.network

import com.and.newton.comms.domain.data.Category
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SlackAPI {

    @POST("/api/chat.postMessage?token=xoxb-991306771874-1280186174754-ZLsCegbIRUT5IOzvgwkZtC7F&channel=C0103JS0DQC&text=hello")
    suspend fun postToSlackChannel(token: String, channel: String,  text: String)

}