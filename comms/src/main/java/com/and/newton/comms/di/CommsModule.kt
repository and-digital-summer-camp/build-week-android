package com.and.newton.comms.di

import com.and.newton.common.NetworkAPI
import com.and.newton.common.mock.MockInterceptor
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.CommsRepositoryImpl
import com.and.newton.comms.network.CommsAPI
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object CommsModule {

    @Singleton
    @Provides
    fun provideNetworkApi(retrofit: Retrofit) : CommsAPI {
        return retrofit.create(CommsAPI::class.java)
    }
}


@Module
@InstallIn(ApplicationComponent::class)
abstract class CommsBindingModule {

    @Binds
    abstract fun bindCommsRepository(
        commsRepositoryImpl: CommsRepositoryImpl
    ): CommsRepository
}


