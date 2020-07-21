package com.and.newton.common.mock

class MockInterceptor {
    //ToDo
}
//
//
//import android.content.Context
//import okhttp3.Interceptor
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.Response
//import okhttp3.ResponseBody.Companion.toResponseBody
//import java.io.IOException
//
//class MockInterceptor: Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//            val responseString = getBurgerRecipes
//
//
//            return chain.proceed(chain.request())
//                .newBuilder()
//                .code(200)
//                .message(responseString)
//                .body(
//                    responseString.toByteArray()
//                        .toResponseBody("application/json".toMediaTypeOrNull())
//                )
//                .addHeader("content-type", "application/json")
//                .build()
//
//    }
//
//    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
//        val jsonString: String
//        try {
//            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
//        } catch (ioException: IOException) {
//            ioException.printStackTrace()
//            return null
//        }
//        return jsonString
//    }
//}
//
