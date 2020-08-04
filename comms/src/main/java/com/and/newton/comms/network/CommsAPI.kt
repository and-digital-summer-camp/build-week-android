package com.and.newton.comms.network

import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.common.domain.data.GoogleUserToken
import retrofit2.http.*


interface CommsAPI {

    /**
     * TODO: STRING RETURN TYPE NEEDS TO BE CHANGED TO ARTICLE DATA MODEL, ATM
     */


    //Gets ALL articles
    @GET("/api/articles")
    suspend fun getArticles() : List<Article>

    //Gets SPECIFIC article by ID
    @GET("/api/articles/{id}")
    suspend fun getArticle(@Path("id") id: Int) : Article

    //Gets ALL articles by SPECIFIC category
    @GET("/api/articles/{category}")
    suspend fun getArticlesByCategory(@Path("category") category: String, @Header("Authorization") JWT_token: String) : String

    //Updates SPECIFIC article
    @PUT("/api/articles/{id}")
    suspend fun updateArticle(@Path("id") id: Int, @Body article: Article) : Article

    //creates article
    @POST("/api/articles")
    suspend fun createArticle(@Body article: Article)

    //Gets ALL categories
    @GET("/api/categories")
    suspend fun getAllCategories() : List<Category>

    @POST("/api/categories")
    suspend fun postCategories(@Body category: Category) : Category

    //Deletes SPECIFIC article
    @DELETE("/api/articles/{id}")
    suspend fun deleteArticle(@Path("id") id: Int, @Header("Authorization") JWT_token: String) : String




}