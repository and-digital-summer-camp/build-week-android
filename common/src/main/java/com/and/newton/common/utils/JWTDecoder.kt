package com.and.newton.common.utils

import com.google.gson.GsonBuilder


object JWTDecoder: JsonDecoder<JWTAuthPayload> {
    val gson = GsonBuilder().create()

    override fun payloadFrom(json: String): JWTAuthPayload {
        return gson.fromJson(json, JWTAuthPayload::class.java)
    }
}