package com.and.newton.comms.network

import com.and.newton.comms.domain.data.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface CommsAPI {

    /**
     * TODO: STRING RETURN TYPE NEEDS TO BE CHANGED TO ARTICLE DATA MODEL, ATM
     */

    //Gets auth users permission
    @POST("/auth/google")
    suspend fun getAuthUser(@Query("Authorization") JWT_token: String) : User

    //Gets ALL articles
    @GET("/articles")
    suspend fun getArticles(@Header("Authorization") JWT_token: String) : List<Article>

    //Gets SPECIFIC article by ID
    @GET("/articles/{id}")
    suspend fun getArticle(@Path("id") id: Int, @Header("Authorization") JWT_token: String) : Article

    //Gets ALL articles by SPECIFIC category
    @GET("/articles/{category}")
    suspend fun getArticlesByCategory(@Path("category") category: String, @Header("Authorization") JWT_token: String) : String

    //Updates SPECIFIC article
    @PUT("/articles/{id}")
    suspend fun updateArticle(@Path("id") id: Int, @Query("article") article: String, @Header("Authorization") JWT_token: String) : String

    //creates article
    @POST("/articles")
    suspend fun createArticle(@Query("article") article: String, @Header("Authorization") JWT_token: String) : Article

    //Gets ALL categories
    @GET("/categories")
    suspend fun getAllCategories(@Header("Authorization") JWT_token: String) : List<Category>

    //Deletes SPECIFIC article
    @DELETE("/articles/{id}")
    suspend fun deleteArticle(@Path("id") id: Int, @Header("Authorization") JWT_token: String) : String

}