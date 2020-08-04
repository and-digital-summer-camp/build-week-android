package com.and.newton.common.di

import com.and.newton.common.mock.MockInterceptor
import com.and.newton.common.network.RequestHeaderInterceptor
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
    fun provideRetrofit(mockInterceptor: MockInterceptor, requestHeaderInterceptor: RequestHeaderInterceptor) : Retrofit {

        val httpLogInterceptor = HttpLoggingInterceptor()
        httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY


        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestHeaderInterceptor)
            .addInterceptor(httpLogInterceptor)
//            .addInterceptor(mockInterceptor)
            .build()

        val gsonBuilder = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val gson = gsonBuilder.create()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://and-newton.co.uk")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


}

