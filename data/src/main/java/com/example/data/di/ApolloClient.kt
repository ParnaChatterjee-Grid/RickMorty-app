package com.example.data.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import dagger.Module
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
object ApolloClient{
    @dagger.Provides
    @javax.inject.Singleton
    fun provideApolloClient(httpClient : OkHttpClient): ApolloClient {
        return ApolloClient.Builder().serverUrl(com.example.data.RickMortConstants.RICK_MORTY_URL)
            .okHttpClient(httpClient)
            .build()
    }

    @dagger.Provides
    @javax.inject.Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()
    }
}

