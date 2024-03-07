package com.example.mealdealapp.di

import com.example.mealdealapp.domain.ApiService
import com.example.mealdealapp.domain.ProductCatalogService
import com.example.mealdealapp.network.ktorAndroidHttpClient
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

/**
 * Created by shanta on 5/3/24
 */

@dagger.Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesHttpClient(): HttpClient{
        return ktorAndroidHttpClient
    }

    @Singleton
    @Provides
    fun providesApiService(httpClient: HttpClient): ApiService = ProductCatalogService(httpClient)

}