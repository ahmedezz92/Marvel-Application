package com.example.marvel.domain.usecase

import com.example.marvel.data.core.data.utils.WrappedResponse
import com.example.marvel.data.model.CharactersResponse
import com.example.marvel.domain.model.BaseResult
import com.example.marvel.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMediaImageUseCase @Inject constructor(private val marvelRepository: MarvelRepository) {
    fun execute(
        imageURI: String
    ): Flow<BaseResult<WrappedResponse<CharactersResponse>>> {
        return marvelRepository.getMediaImages(imageURI)
    }
}