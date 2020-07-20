package com.and.newton.common.di

import com.and.newton.common.ArticleAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ArticleRetrofitModule {

    @Singleton
    @Provides
    fun provideArticleApi() : ArticleAPI {
        return Retrofit.Builder()
            .baseUrl(" https://www.google.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleAPI::class.java)
    }
}