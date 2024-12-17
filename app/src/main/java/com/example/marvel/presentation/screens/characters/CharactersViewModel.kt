package com.example.marvel.presentation.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.data.core.data.utils.ErrorResponse
import com.example.marvel.data.model.CharactersResponse
import com.example.marvel.domain.model.BaseResult
import com.example.marvel.domain.model.Character
import com.example.marvel.domain.usecase.GetCharactersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersListUseCase: GetCharactersListUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<GetCharacterState>(GetCharacterState.IsLoading)
    val state: StateFlow<GetCharacterState> =
        _state.asStateFlow()

    private val _characterList = MutableStateFlow<List<Character>>(emptyList())
    val characterList: StateFlow<List<Character>> = _characterList


    private var currentPage = 0
    private val pageSize = 20

    /*loading progress for loading state*/
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    /*error state*/
    private val _errorResponse = MutableStateFlow<ErrorResponse?>(null)
    val errorResponse: StateFlow<ErrorResponse?> = _errorResponse

    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> = _searchQuery.asStateFlow()

    private var _totalCharacters = MutableStateFlow(0)
    val totalCharacters: StateFlow<Int> = _totalCharacters.asStateFlow()


    init {
        getCharactersList()
    }

    fun getCharactersList() {
        viewModelScope.launch {
            getCharactersListUseCase.execute(
                offset = currentPage * pageSize, limit = pageSize,
                name = _searchQuery.value
            )
                .onStart {
                    setLoading()
                }
                .catch {
                    hideLoading()
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.ErrorState -> {
                            _errorResponse.value = result.errorResponse
                        }

                        is BaseResult.DataState -> {
                            result.items?.data?.total?.let { total ->
                                _totalCharacters.value = total
                            }
                            result.items?.data?.results?.let { newResults ->
                                val updatedList = _characterList.value + newResults
                                _characterList.value = updatedList
                                currentPage++
                            }
                        }
                    }
                }
        }
    }

    fun searchCharacters(query: String?) {
        resetList()
        if (query.isNullOrEmpty())
            _searchQuery.value = null
        else
            _searchQuery.value = query
        getCharactersList()
    }

    private fun resetList() {
        _characterList.value = emptyList()
        currentPage = 0
        _totalCharacters.value = 0
    }

    private fun setLoading() {
        _isLoading.value = true
    }

    private fun hideLoading() {
        _isLoading.value = false
    }

    fun getCharacterById(characterId: Int): Character? {
        return _characterList.value.find { it.id == characterId }
    }
}

sealed class GetCharacterState {
    object IsLoading : GetCharacterState()
    data class Success(val data: CharactersResponse) : GetCharacterState()
    data class Error(val errorResponse: ErrorResponse) : GetCharacterState()
}