package com.example.marvel.presentation.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.data.core.data.utils.ErrorResponse
import com.example.marvel.domain.model.BaseResult
import com.example.marvel.domain.model.Character
import com.example.marvel.domain.usecase.GetCharactersListUseCase
import com.example.marvel.domain.utils.NetworkUtils
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
    private val getCharactersListUseCase: GetCharactersListUseCase,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val _state =
        MutableStateFlow<GetCharacterState>(GetCharacterState.Init)
    val state: StateFlow<GetCharacterState> =
        _state.asStateFlow()

    private val _characterList = MutableStateFlow<List<Character>>(emptyList())
    val characterList: StateFlow<List<Character>> = _characterList

    private val _isNetworkAvailable = MutableStateFlow(true)
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable.asStateFlow()

    private val _isQueriedBefore = MutableStateFlow(false)
    val isQueriedBefore: StateFlow<Boolean> = _isQueriedBefore.asStateFlow()

    private var currentPage = 0
    private val pageSize = 20

    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> = _searchQuery.asStateFlow()

    private var _totalCharacters = MutableStateFlow(0)
    val totalCharacters: StateFlow<Int> = _totalCharacters.asStateFlow()

    init {
        _isNetworkAvailable.value = networkUtils.isNetworkAvailable()
        getCharactersList()
    }

    fun getCharactersList() {
        if (!_isNetworkAvailable.value) {
            return
        }
        viewModelScope.launch {
            getCharactersListUseCase.execute(
                offset = currentPage * pageSize, limit = pageSize,
                name = _searchQuery.value
            )
                .onStart {
                    _state.value = if (currentPage == 0)
                        GetCharacterState.IsLoading
                    else
                        GetCharacterState.IsPaginating
                }
                .catch {
                    _state.value = GetCharacterState.Error(it.message!!)
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.ErrorState -> {
                            _state.value = GetCharacterState.Error(result.errorResponse.message)
                        }

                        is BaseResult.DataState -> {
                            result.items?.data?.total?.let { total ->
                                if (total == 0)
                                    _state.value = GetCharacterState.Empty
                                else {
                                    _totalCharacters.value = total
                                    result.items.data.results.let { newResults ->
                                        val updatedList = _characterList.value + newResults
                                        _state.value = GetCharacterState.Success(updatedList)
                                        _characterList.value = updatedList
                                        currentPage++
                                    }
                                }
                            }
//                            result.items?.data?.total?.let { total ->
//                                _totalCharacters.value = total
//                            }
//                            result.items?.data?.results?.let { newResults ->
//
//                            }
                        }
                    }
                }
        }
    }

    fun searchCharacters(query: String?) {
        resetList()
        if (query.isNullOrEmpty()) {
            _searchQuery.value = null
            _isQueriedBefore.value = false
        } else {
            _searchQuery.value = query
            _isQueriedBefore.value = true
        }
        getCharactersList()
    }

    fun resetList() {
        if (!networkUtils.isNetworkAvailable()) {
            _isNetworkAvailable.value = false
            return
        }
        _isNetworkAvailable.value = true
        _characterList.value = emptyList()
        _state.value = GetCharacterState.Init
        currentPage = 0
        _totalCharacters.value = 0
    }

    fun getCharacterById(characterId: Int): Character? {
        _isNetworkAvailable.value = networkUtils.isNetworkAvailable()
        return _characterList.value.find { it.id == characterId }
    }
}

sealed class GetCharacterState {
    object Init : GetCharacterState()
    object IsLoading : GetCharacterState()

    object IsPaginating : GetCharacterState()

    data class Success(val data: List<Character>) : GetCharacterState()
    data class Error(val message: String) : GetCharacterState()
    object Empty : GetCharacterState()
}