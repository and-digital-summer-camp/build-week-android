package com.and.newton.common.mock

import com.and.newton.common.mock.comms.CommsResponseMapper
import javax.net.ssl.HttpsURLConnection

class ResponseMapper {

    companion object {
        operator fun Regex.contains(text: CharSequence): Boolean = this.matches(text)

        fun getResponseBody(uriPath:String, httpMethod:String = URIConstants.HTTP_GET ):String {
            when(uriPath) {
                in URIConstants.ARTICLE_API -> return CommsResponseMapper.getArticleAPi(httpMethod)
                in URIConstants.USER_API -> return "getAuthUser.json"
                in Regex(URIConstants.ARTICLE_API+"/\\d") -> return "getArticle.json"
            }
            return ""
        }

        fun getResponseCode(uriPath:String ):Int {
            when(uriPath) {
                URIConstants.USER_API -> return HttpsURLConnection.HTTP_CREATED
            }
            return HttpsURLConnection.HTTP_OK
        }

    }
}