package com.and.newton.common.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class RequestHeaderInterceptor @Inject constructor() :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val authToken = AppPreferences.authToken

        val requestBuilder: Request.Builder = chain.request().newBuilder().header("Authorization", "eyasdasdasdasdasdasd")
            //if(authToken!=null && authToken.isNotEmpty()) chain.request().newBuilder().header("Authorization", authToken) else chain.request().newBuilder()


        return chain.proceed(requestBuilder.build())
            .newBuilder()
            .build()

    }
}

