package com.and.newton.comms.network

import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import retrofit2.Response
import retrofit2.http.*

interface CommsAPI {

    //Gets ALL articles
    @GET("/api/articles")
    suspend fun getArticles() : Response<List<Article>>

    //Gets SPECIFIC article by ID
    @GET("/api/articles/{id}")
    suspend fun getArticle(@Path("id") id: Int) : Response<Article>

    //Gets ALL articles by SPECIFIC category
    @GET("/api/articles/{category}")
    suspend fun getArticlesByCategory(@Path("category") category: String) : String

    //Updates SPECIFIC article
    @PUT("/api/articles/{id}")
    suspend fun updateArticle(@Path("id") id: Int, @Body article: Article) : Response<Article>

    //creates article
    @POST("/api/articles")
    suspend fun createArticle(@Body article: Article) : Response<Article>

    //Gets ALL categories
    @GET("/api/categories")
    suspend fun getAllCategories() : Response<List<Category>>

    @POST("/api/categories")
    suspend fun postCategories(@Body category: Category) : Response<Category>

    //Deletes SPECIFIC article
    @DELETE("/api/articles/{id}")
    suspend fun deleteArticle(@Path("id") id: Int) : String

}