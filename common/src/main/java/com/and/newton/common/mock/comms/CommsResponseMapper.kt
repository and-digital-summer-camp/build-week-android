package com.and.newton.common.mock.comms

import com.and.newton.common.mock.URIConstants

class CommsResponseMapper {
    companion object {
         fun getArticleAPi(httpMethod:String):String {
            when(httpMethod) {
                URIConstants.HTTP_GET -> return "getArticles.json"
                URIConstants.HTTP_POST -> return "postArticle.json"
                URIConstants.HTTP_PUT -> return "postArticle.json"
            }
            return ""
        }

        fun getCategoriesAPi(httpMethod:String):String {
            when(httpMethod) {
                URIConstants.HTTP_GET -> return "getCategories.json"
            }
            return ""
        }
    }
}