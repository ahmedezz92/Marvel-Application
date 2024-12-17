package com.example.marvel.di

import com.example.marvel.data.core.data.module.NetworkModule
import com.example.marvel.data.datasource.remote.MarvelApi
import com.example.marvel.data.repository.MarvelRepositoryImpl
import com.example.marvel.domain.repository.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): MarvelApi {
        return retrofit.create(MarvelApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(
        marvelApi: MarvelApi,
    ): MarvelRepository {
        return MarvelRepositoryImpl(marvelApi)
    }
}