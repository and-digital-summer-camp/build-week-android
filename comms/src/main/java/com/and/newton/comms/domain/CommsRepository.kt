package com.and.newton.comms.domain

import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.common.domain.data.User

interface CommsRepository {
    suspend fun getUser(token: String): User?

    suspend fun getArticles(): List<Article>?

    suspend fun getArticle(id: Int): Article?

    suspend fun getCategories(): List<Category>?
}

