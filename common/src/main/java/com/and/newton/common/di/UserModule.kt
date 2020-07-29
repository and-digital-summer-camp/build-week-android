package com.and.newton.common.di

import com.and.newton.common.domain.UserRepo
import com.and.newton.common.domain.UserRepoImpl
import com.and.newton.common.network.UserAPI
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object UserModule {

    @Singleton
    @Provides
    fun provideNetworkApi(retrofit: Retrofit) : UserAPI {
        return retrofit.create(UserAPI::class.java)
    }
}


@Module
@InstallIn(ApplicationComponent::class)
abstract class UserBindingModule {
    @Binds
    abstract fun bindUserRepo(
        userRepoImpl: UserRepoImpl
    ): UserRepo
}