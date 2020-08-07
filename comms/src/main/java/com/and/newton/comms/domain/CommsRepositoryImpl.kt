package com.and.newton.comms.domain

import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.comms.network.ApiData
import com.and.newton.comms.network.CommsAPI
import com.and.newton.comms.network.ResponseHandler
import timber.log.Timber
import javax.inject.Inject

class CommsRepositoryImpl @Inject constructor(private val commsAPI: CommsAPI) : CommsRepository {

    override suspend fun getArticles(): ApiData<List<Article>> {
        val getAllArticlesResponse = commsAPI.getArticles()
        Timber.d("Get All Articles API Response: $getAllArticlesResponse")
        return ResponseHandler.handleResponse(getAllArticlesResponse)
    }

    override suspend fun getArticle(id: Int): ApiData<Article> {
        val getArticleResponse = commsAPI.getArticle(id)
        Timber.d("Get Article API Response: $getArticleResponse")
        return ResponseHandler.handleResponse(getArticleResponse)
    }

    override suspend fun createArticle(article: Article) : ApiData<Article> {
        val createResponse = commsAPI.createArticle(article)
        Timber.d("Create Article API Call Response: $createResponse")
        return ResponseHandler.handleResponse(createResponse)
    }

    override suspend fun updateArticle(id : Int, article: Article) : ApiData<Article> {
        val updateResponse = commsAPI.updateArticle(id, article)
        Timber.d("Update Article API Call Response: $updateResponse")
        return ResponseHandler.handleResponse(updateResponse)
    }

    override suspend fun getCategories(): ApiData<List<Category>> {
        val getCategoriesResponse = commsAPI.getAllCategories()
        Timber.d("Get all Categories API Response: $getCategoriesResponse")
        return ResponseHandler.handleResponse(getCategoriesResponse)
    }

    override suspend fun createCategory(category: Category): ApiData<Category> {
        val createCategoryResponse = commsAPI.postCategories(category)
        Timber.d("Create Category API Response: $createCategoryResponse")
        return ResponseHandler.handleResponse(createCategoryResponse)
    }

}