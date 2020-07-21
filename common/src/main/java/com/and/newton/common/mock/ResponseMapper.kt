package com.and.newton.common.mock

import javax.net.ssl.HttpsURLConnection

class ResponseMapper {

    companion object {
        fun getResponseBody(uriPath:String, httpMethod:String = "GET" ):String {
            when(uriPath) {
                URIConstants.ARTICLE_API -> return "getArticles.json"
                URIConstants.USER_API -> return "getAuthUser.json"
            }
            return ""
        }

        fun getResponseCode(uriPath:String, httpMethod:String = "GET" ):Int {
            when(uriPath) {
                URIConstants.USER_API -> return HttpsURLConnection.HTTP_CREATED
            }
            return HttpsURLConnection.HTTP_OK
        }

    }
}