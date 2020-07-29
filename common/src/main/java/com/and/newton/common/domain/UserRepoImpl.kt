package com.and.newton.common.domain

import com.and.newton.common.domain.data.AppJwtUserToken
import com.and.newton.common.domain.data.GoogleUserToken
import com.and.newton.common.network.UserAPI
import javax.inject.Inject

class UserRepoImpl @Inject constructor(val userAPI: UserAPI) : UserRepo {
    override suspend fun getUser(token:GoogleUserToken): AppJwtUserToken? {
        return userAPI.getAuthUser(token)
    }
}