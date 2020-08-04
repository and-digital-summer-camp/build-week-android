package com.and.newton.comms.domain

import com.and.newton.comms.network.SlackAPI
import javax.inject.Inject

class SlackRepoImpl @Inject constructor( private val slackAPI: SlackAPI) : SlackRepo {
    override suspend fun postToSlackChannel(token: String, channel: String,  text: String) {
        slackAPI.postToSlackChannel(token, channel, text)
    }
}