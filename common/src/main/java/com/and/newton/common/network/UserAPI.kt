package com.and.newton.common.network

import com.and.newton.common.domain.data.AppJwtUserToken
import com.and.newton.common.domain.data.GoogleUserToken
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
    //Gets auth users permission
    @POST("/api/auth/google")
    suspend fun getAuthUser(@Body token: GoogleUserToken) : AppJwtUserToken
}