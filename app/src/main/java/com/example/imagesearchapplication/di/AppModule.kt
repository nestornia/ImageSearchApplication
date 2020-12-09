package com.example.imagesearchapplication.di

import com.example.imagesearchapplication.data.api.UnsplashApi
import com.example.imagesearchapplication.data.api.UnsplashApi.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnpslashApi(retrofit: Retrofit): UnsplashApi =
        retrofit.create(UnsplashApi::class.java)

}