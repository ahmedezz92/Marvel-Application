package com.example.marvel.presentation.screens.characters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.marvel.presentation.components.SearchBar
import com.example.marvel.presentation.components.list.CharacterItem
import com.example.marvel.presentation.components.states.ErrorState
import com.example.marvel.presentation.components.states.LoadingState

@Composable
fun CharactersList(charactersViewModel: CharactersViewModel, navController: NavHostController) {

    val charactersList by charactersViewModel.characterList.collectAsState()
    val searchQuery by charactersViewModel.searchQuery.collectAsState()
    val totalCharacters by charactersViewModel.totalCharacters.collectAsState()
    val currentState by charactersViewModel.state.collectAsState()

    val listState = rememberLazyListState()
    val isAtEnd = remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisibleItemIndex == (charactersList.size - 1)
        }
    }
    LaunchedEffect(isAtEnd.value) {
        if (isAtEnd.value && charactersList.size < totalCharacters) {
            println("Reached the end of the list")
            charactersViewModel.getCharactersList()
        }
    }
    Column(
        modifier = Modifier
            .padding()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            onSearch = { name ->
                if (searchQuery != name) {
                    charactersViewModel.searchCharacters(name)
                }
            }, modifier = Modifier.padding()

        )
        when (val state = currentState) {
            is GetCharacterState.IsLoading -> {
                LoadingState()
            }

            is GetCharacterState.Error -> {
                ErrorState(error = state.message)
            }

            is GetCharacterState.Success,GetCharacterState.IsPaginating -> {
                LazyColumn(
                    modifier = Modifier.padding(),
                    state = listState
                ) {
                    items(charactersList) { character ->
                        CharacterItem(character = character,
                            onCharacterClick = { characterId ->
                                navController.navigate("charDetails/$characterId")
                            })
                    }
                    if (currentState is GetCharacterState.IsPaginating && charactersList.size < totalCharacters) {
                        item {
                            LoadingState()
                        }
                    }
                }
            }
         /*   is GetCharacterState.IsPaginating -> {
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