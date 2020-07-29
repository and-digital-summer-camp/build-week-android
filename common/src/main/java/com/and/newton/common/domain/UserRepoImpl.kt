package com.and.newton.common.domain

import com.and.newton.common.domain.data.User
import com.and.newton.common.network.UserAPI
import javax.inject.Inject

class UserRepoImpl @Inject constructor(val userAPI: UserAPI) : UserRepo {
    override suspend fun getUser(token: String): User? {
        return userAPI.getAuthUser(token)
    }
}