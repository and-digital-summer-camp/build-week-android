package com.and.newton.common.mock

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import javax.inject.Inject

class MockInterceptor @Inject constructor(@ApplicationContext private val context: Context) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri: Uri = Uri.parse(chain.request().url.toString())


        val responseDataFileName: String = ResponseMapper.getResponseBody(
            uri.path ?: URIConstants.USER_API,
            chain.request().method
        )
        val responseString: String = getJsonDataFromAsset(context, responseDataFileName)

        val responseStatusCode: Int =
            ResponseMapper.getResponseCode(uri.path ?: URIConstants.USER_API)



        return chain.proceed(chain.request())
            .newBuilder()
            .code(responseStatusCode)
            .message(responseString)
            .body(
                responseString.toByteArray()
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()

    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return """{"error":"Mock Response data json file not found"}"""
        }
        return jsonString
    }
}

