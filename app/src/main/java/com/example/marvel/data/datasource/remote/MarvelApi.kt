package com.example.marvel.data.datasource.remote

import com.example.marvel.data.core.data.utils.WrappedResponse
import com.example.marvel.data.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MarvelApi {
    @GET("characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("name")name:String?,
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: String,
        @Query("hash") hash: String
    ): Response<WrappedResponse<CharactersResponse>>

    @GET
    suspend fun getResourceImage(
        @Url url: String,
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: String,
        @Query("hash") hash: String
    ): Response<WrappedResponse<CharactersResponse>>
}