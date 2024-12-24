package com.example.marvel.presentation.screens.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.marvel.R
import com.example.marvel.presentation.components.AnimatedSearchBar
import com.example.marvel.presentation.components.connection.NoInternetConnection
import com.example.marvel.presentation.components.list.CharacterItem
import com.example.marvel.presentation.components.states.EmptyState
import com.example.marvel.presentation.components.states.ErrorState
import com.example.marvel.presentation.components.states.LoadingState


@Composable
fun CharactersList(charactersViewModel: CharactersViewModel, navController: NavHostController) {

    val charactersList by charactersViewModel.characterList.collectAsState()
    val searchQuery by charactersViewModel.searchQuery.collectAsState()
    val totalCharacters by charactersViewModel.totalCharacters.collectAsState()
    val currentState by charactersViewModel.state.collectAsState()
    val isNetworkAvailable by charactersViewModel.isNetworkAvailable.collectAsState()
    val isQueriedBefore by charactersViewModel.isQueriedBefore.collectAsState()

    val listState = rememberLazyListState()
    val isAtEnd = remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisibleItemIndex == (charactersList.size - 1)
        }
    }
    LaunchedEffect(isAtEnd.value) {
        if (isAtEnd.value && charactersList.size < totalCharacters) {
            charactersViewModel.getCharactersList()
        }
    }

    if (!isNetworkAvailable && charactersList.isEmpty()) {
        NoInternetConnection(onRetry = {
            charactersViewModel.getCharactersList()
        })
    } else {
        Column(
            modifier = Modifier
                .padding()
                .fillMaxSize()
                .background(color = colorResource(id = R.color.color_bg)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedSearchBar(
                onSearch = { name, isCancel ->
                    if (name != searchQuery && !isCancel)
                        charactersViewModel.searchCharacters(name)
                    else if (isCancel && isQueriedBefore)
                        charactersViewModel.searchCharacters(null)

                }, modifier = Modifier.padding()

            )
            when (val state = currentState) {
                is GetCharacterState.Empty -> {
                    EmptyState(empty = stringResource(id = R.string.label_empty_result))
                }

                is GetCharacterState.IsLoading -> {
                    LoadingState()
                }

                is GetCharacterState.Error -> {
                    ErrorState(error = state.message)
                }

                is GetCharacterState.Success, GetCharacterState.IsPaginating -> {
                    LazyColumn(
                        modifier = Modifier.padding(), state = listState
                    ) {
                        items(charactersList) { character ->
                            CharacterItem(character = character, onCharacterClick = { characterId ->
                                navController.navigate("charDetails/$characterId")
                            })
                        }
                        if (currentState is GetCharacterState.IsPaginating && charactersList.size < totalCharacters) {
                            item {
                                LoadingState()
                            }
                        }
                    }
                }/*   is GetCharacterState.IsPaginating -> {
                       LazyColumn(
                           modifier = Modifier.padding(),
                           state = listState
                       ) {
                           items(charactersList) { character ->
                               CharacterItem(
                                   character = character,
                                   onCharacterClick = { characterId ->
                                       navController.navigate("charDetails/$characterId")
                                   }
                               )
                           }

                           item {
                               Box(
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(16.dp),
                                   contentAlignment = Alignment.Center
                               ) {
                                   LoadingState()
                               }
                           }
                       }
                   }
       */
                else -> {}
            }
        }
    }
}