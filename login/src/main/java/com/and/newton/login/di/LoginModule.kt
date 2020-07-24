package com.and.newton.login.di

import com.and.newton.login.domain.LoginRepo
import com.and.newton.login.domain.LoginRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class LoginModule {
    @Binds
    abstract fun bindLoginRepo(loginRepoImpl: LoginRepoImpl) : LoginRepo
}

