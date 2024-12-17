package com.example.marvel.data.repository

import com.example.marvel.data.core.data.utils.WrappedErrorResponse
import com.example.marvel.data.core.data.utils.WrappedResponse
import com.example.marvel.data.datasource.remote.MarvelApi
import com.example.marvel.data.model.CharactersResponse
import com.example.marvel.domain.model.BaseResult
import com.example.marvel.domain.repository.MarvelRepository
import com.example.marvelapp.domain.utils.Constants
import com.example.marvel.domain.utils.md5
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val api: MarvelApi
) : MarvelRepository {
    override fun getCharactersList(
        offset: Int,
        limit: Int,
        name: String?
    ): Flow<BaseResult<WrappedResponse<CharactersResponse>>> {
        return flow {
            val timestamp = System.currentTimeMillis().toString()
            val hash = generateHash(timestamp)
            val response = api.getCharacters(
                offset,
                limit,
                name = name,
                Constants.Authorization.API_KEY_PUBLIC,
                timestamp,
                hash
            )
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))
            } else {
                val errorBody = response.errorBody()?.charStream()
                val type = object : TypeToken<WrappedErrorResponse>() {}.type
                val errorResponse: WrappedErrorResponse =
                    Gson().fromJson(errorBody, type)
                emit(BaseResult.ErrorState(errorResponse.errorResponse))
            }
        }
    }

    override fun getMediaImages(
        imageURI: String
    ): Flow<BaseResult<WrappedResponse<CharactersResponse>>> {
        return flow {
            val timestamp = System.currentTimeMillis().toString()
            val hash = generateHash(timestamp)
            val response = api.getResourceImage(
                url = imageURI,
                Constants.Authorization.API_KEY_PUBLIC,
                timestamp,
                hash
            )
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))
            } else {
                val errorBody = response.errorBody()?.charStream()
                val type = object : TypeToken<WrappedErrorResponse>() {}.type
                val errorResponse: WrappedErrorResponse =
                    Gson().fromJson(errorBody, type)
                emit(BaseResult.ErrorState(errorResponse.errorResponse))
            }
        }
    }

    private fun generateHash(timestamp: String): String {
        val input =
            timestamp + Constants.Authorization.API_KEY_PRIVATE + Constants.Authorization.API_KEY_PUBLIC
        return input.md5()
    }
}
