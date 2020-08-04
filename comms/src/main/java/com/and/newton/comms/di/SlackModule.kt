package com.and.newton.comms.di

import com.and.newton.common.mock.MockInterceptor
import com.and.newton.common.network.RequestHeaderInterceptor
import com.and.newton.comms.domain.CommsRepository
import com.and.newton.comms.domain.CommsRepositoryImpl
import com.and.newton.comms.domain.SlackRepo
import com.and.newton.comms.domain.SlackRepoImpl
import com.and.newton.comms.network.CommsAPI
import com.and.newton.comms.network.SlackAPI
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object SlackModule {

    @Singleton
    @Provides
    fun provideNetworkApi(): SlackAPI {
        return Retrofit.Builder()
            .baseUrl("https://slack.com")
            .build().create(SlackAPI::class.java)
    }
}


@Module
@InstallIn(ApplicationComponent::class)
abstract class SlackBindingModule {

    @Binds
    abstract fun bindSlackRepo(
        slackRepoImpl: SlackRepoImpl
    ): SlackRepo
}
