package com.and.newton.comms.domain

import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.comms.network.ApiData
import retrofit2.Response

interface CommsRepository {

    suspend fun getArticles(): ApiData<List<Article>>

    suspend fun getArticle(id: Int): ApiData<Article>

    suspend fun createArticle(article: Article) : ApiData<Article>

    suspend fun updateArticle(id : Int, article: Article) : ApiData<Article>

    suspend fun getCategories(): ApiData<List<Category>>

    suspend fun createCategory(category: Category) : ApiData<Category>
}

