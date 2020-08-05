package com.and.newton.common.network

import com.and.newton.common.utils.AppPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class RequestHeaderInterceptor @Inject constructor() :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val authToken = AppPreferences.token

        val requestBuilder: Request.Builder = if(authToken!=null && authToken.isNotEmpty()) chain.request().newBuilder().addHeader("Authorization", "Bearer $authToken") else chain.request().newBuilder()


        return chain.proceed(requestBuilder.build())

    }
}

