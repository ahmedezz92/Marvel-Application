package com.example.marvel.domain.repository

import com.example.marvel.data.core.data.utils.WrappedResponse
import com.example.marvel.data.model.CharactersResponse
import com.example.marvel.domain.model.BaseResult
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {
     fun getCharactersList(
         offset: Int,
         limit: Int,
         name: String?
     ): Flow<BaseResult<WrappedResponse<CharactersResponse>>>

    fun getMediaImages(
        imageURI:String,
    ): Flow<BaseResult<WrappedResponse<CharactersResponse>>>

}