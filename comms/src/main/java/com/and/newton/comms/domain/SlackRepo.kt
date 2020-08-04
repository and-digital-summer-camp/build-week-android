package com.and.newton.comms.domain

import com.and.newton.comms.domain.data.Article

interface SlackRepo {
    suspend fun postToSlackChannel(token: String, channel: String, text: String)
}