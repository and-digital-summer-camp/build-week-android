package com.and.newton.common.network

import com.and.newton.common.domain.data.User
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAPI {
    //Gets auth users permission
    @POST("/auth/google")
    suspend fun getAuthUser(@Query("Authorization") JWT_token: String) : User
}