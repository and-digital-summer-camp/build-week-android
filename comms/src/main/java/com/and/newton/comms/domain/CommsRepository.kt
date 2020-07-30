package com.and.newton.comms.domain

import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.common.domain.data.GoogleUserToken

interface CommsRepository {

    suspend fun getArticles(): List<Article>?

    suspend fun getArticle(id: Int): Article?

    suspend fun createArticle(article: Article)

    suspend fun updateArticle(id : Int, article: Article)

    suspend fun getCategories(): List<Category>?

    suspend fun createCategory(category: Category)
}

