package com.example.marvel.presentation.screens.chardetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.marvel.presentation.components.connection.NoInternetConnection
import com.example.marvel.presentation.components.generics.MediaSection
import com.example.marvel.presentation.components.generics.MediaType
import com.example.marvel.presentation.screens.characters.CharactersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetails(
    charactersViewModel: CharactersViewModel,
    characterId: Int,
    navController: NavController
) {
    val character = charactersViewModel.getCharacterById(characterId)
    val isNetworkAvailable by charactersViewModel.isNetworkAvailable.collectAsState()

    character?.let {
        Scaffold(topBar = {
            TopAppBar(title = { }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            })
        }) { paddingValues ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                val imageURL =
                    character.thumbnail.path.plus(".".plus(character.thumbnail.extension))

                AsyncImage(
                    model = imageURL,
                    contentDescription = "Marvel banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    character.name?.let { it1 ->
                        Text(
                            text = it1,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    if (character.description.isNotEmpty())
                        Text(
                            text = character.description,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    if (!isNetworkAvailable) {
                        NoInternetConnection(
                            onRetry = null
                        )
                    } else {
                        if (character.comics.items.isNotEmpty())
                            MediaSection(
                                title = "Comics",
                                items = character.comics.items,
                                mediaType = MediaType.COMICS
                            )
                        if (character.series.items.isNotEmpty())
                            MediaSection(
                                title = "Series",
                                items = character.series.items,
                                mediaType = MediaType.SERIES
                            )
                        if (character.events.items.isNotEmpty())
                            MediaSection(
                                title = "Events",
                                items = character.events.items,
                                mediaType = MediaType.EVENTS
                            )
                        if (character.stories.items.isNotEmpty())
                            MediaSection(
                                title = "Stories",
                                items = character.stories.items,
                                mediaType = MediaType.STORIES
                            )
                    }
                }
            }
        }
    }
}