package com.and.newton.comms.domain

import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.common.domain.data.GoogleUserToken
import com.and.newton.comms.network.CommsAPI
import javax.inject.Inject

class CommsRepositoryImpl @Inject constructor(private val commsAPI: CommsAPI) : CommsRepository {
    var google_auth_token:String = "Bearer xxxxxxx"

//    override suspend fun getUser(token: String): GoogleUserToken? {
//        google_auth_token = token
//        return commsAPI.getAuthUser()
//    }

    override suspend fun getArticles(): List<Article>? {
        return commsAPI.getArticles()
    }

    override suspend fun getArticle(id: Int): Article? {
        return commsAPI.getArticle(id)
    }

    override suspend fun createArticle(article: Article) {
         commsAPI.createArticle(article)

    }
    override suspend fun getCategories(): List<Category>? {
        return commsAPI.getAllCategories()
    }


}