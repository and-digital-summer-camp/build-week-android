package com.and.newton.comms.network

import com.and.newton.comms.domain.data.Articles
import com.and.newton.comms.domain.data.User
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
    suspend fun getArticles(@Header("Authorization") JWT_token: String) : Articles

    //Gets SPECIFIC article by ID
    @GET("/articles/{id}")
    suspend fun getArticle(@Path("id") id: Int, @Header("JWT token") JWT_token: String) : String

    //Gets ALL articles by SPECIFIC category
    @GET("/articles/{category}")
    suspend fun getArticlesByCategory(@Path("category") category: String, @Header("JWT token") JWT_token: String) : String

    //Updates SPECIFIC article
    @PUT("/articles/{id}")
    suspend fun updateArticle(@Path("id") id: Int, @Query("article") article: String, @Header("JWT token") JWT_token: String) : String

    //creates article
    @POST("/articles")
    suspend fun createArticle(@Query("article") article: String, @Header("JWT token") JWT_token: String) : String

    //Gets ALL categories
    @GET("/categories")
    suspend fun getAllCategories(@Header("JWT token") JWT_token: String) : String

    //Deletes SPECIFIC article
    @DELETE("/articles/{id}")
    suspend fun deleteArticle(@Path("id") id: Int, @Header("JWT token") JWT_token: String) : String

}