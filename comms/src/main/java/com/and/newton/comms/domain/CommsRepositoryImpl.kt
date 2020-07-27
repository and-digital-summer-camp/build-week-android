package com.and.newton.comms.domain

import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.comms.domain.data.User
import com.and.newton.comms.network.CommsAPI
import javax.inject.Inject

class CommsRepositoryImpl @Inject constructor(private val commsAPI: CommsAPI) : CommsRepository {
    var google_auth_token:String = "Bearer xxxxxxx"

    override suspend fun getUser(token: String): User? {
        google_auth_token = token
        return commsAPI.getAuthUser(google_auth_token)
    }

    override suspend fun getArticles(): List<Article>? {
        return commsAPI.getArticles(google_auth_token)
    }

    override suspend fun getArticle(id: Int): Article? {
        return commsAPI.getArticle(id, google_auth_token)
    }

    override suspend fun createArticle(article: String): Article? {
        return commsAPI.createArticle(article, google_auth_token)

    }

    override suspend fun getAllCategories(): List<Category> {
        return commsAPI.getAllCategories(google_auth_token)
    }

}