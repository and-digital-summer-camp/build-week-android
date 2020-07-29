package com.and.newton.common.domain

import com.and.newton.common.domain.data.User

interface UserRepo {
    suspend fun getUser(token: String): User?
}