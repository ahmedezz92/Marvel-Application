package com.example.marvel.domain.model

import com.example.marvel.data.core.data.utils.WrappedErrorResponse

sealed class BaseResult<out T> {
    data class DataState<T : Any>(val items: T?) : BaseResult<T>()
    data class ErrorState(val errorResponse: WrappedErrorResponse) : BaseResult<Nothing>()
}