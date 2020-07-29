package com.and.newton.common.domain

import com.and.newton.common.domain.data.AppJwtUserToken
import com.and.newton.common.domain.data.GoogleUserToken

interface UserRepo {
    suspend fun getUser(token :GoogleUserToken): AppJwtUserToken?
}