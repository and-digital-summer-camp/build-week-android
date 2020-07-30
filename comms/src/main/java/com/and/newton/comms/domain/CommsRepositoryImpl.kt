package com.and.newton.comms.domain

import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.comms.network.CommsAPI
import javax.inject.Inject

class CommsRepositoryImpl @Inject constructor(private val commsAPI: CommsAPI) : CommsRepository {

    override suspend fun getArticles(): List<Article>? {
        return commsAPI.getArticles()
    }

    override suspend fun getArticle(id: Int): Article? {
        return commsAPI.getArticle(id)
    }

    override suspend fun createArticle(article: Article) {
         commsAPI.createArticle(article)
    }

    override suspend fun updateArticle(id : Int, article: Article) {
        commsAPI.updateArticle(id, article)
    }

    override suspend fun getCategories(): List<Category>? {
        return commsAPI.getAllCategories()
    }

    override suspend fun createCategory(category: Category) {
        commsAPI.postCategories(category)
    }


}