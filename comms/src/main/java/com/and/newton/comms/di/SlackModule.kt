package com.and.newton.comms.di

import com.and.newton.comms.domain.SlackRepo
import com.and.newton.comms.domain.SlackRepoImpl
import com.and.newton.comms.network.SlackAPI
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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
