package com.and.newton.common.di

import com.and.newton.common.mock.MockInterceptor
import com.google.gson.GsonBuilder
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
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(mockInterceptor: MockInterceptor) : Retrofit {

        val httpLogInterceptor = HttpLoggingInterceptor()
        httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY


        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLogInterceptor)
            .addInterceptor(mockInterceptor)
            .build()

        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(" https://www.google.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}

