package com.example.marvel.presentation.screens.chardetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.marvel.R
import com.example.marvel.presentation.components.connection.NoInternetConnection
import com.example.marvel.presentation.components.generics.MediaSection
import com.example.marvel.presentation.components.generics.MediaType
import com.example.marvel.presentation.screens.characters.CharactersViewModel

@Composable
fun CharacterDetails(
    charactersViewModel: CharactersViewModel,
    characterId: Int,
    navController: NavController
) {
    val character = charactersViewModel.getCharacterById(characterId)
    val isNetworkAvailable by charactersViewModel.isNetworkAvailable.collectAsState()

    character?.let {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.color_bg))
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                ) {
                    val imageURL = character.thumbnail.path.plus(".".plus(character.thumbnail.extension))
                    AsyncImage(
                        model = imageURL,
                        contentDescription = "Marvel banner",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(6.dp)
//                            .background(
//                                color = Color.Black.copy(alpha = 0.5f),
//                                shape = CircleShape
//                            )
                    ) {
                        Icon(
                            Icons.Filled.KeyboardArrowLeft,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    character.name?.let {
                        Text(
                            text = stringResource(id = R.string.label_name),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    if (character.description.isNotEmpty()) {
                        Text(
                            text = stringResource(id = R.string.label_description),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                            modifier = Modifier.padding(4.dp)
                            )
                        Text(
                            text = character.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    if (!isNetworkAvailable) {
                        NoInternetConnection(
                            onRetry = null
                        )
                    }
                    else {
                        if (character.comics.items.isNotEmpty())
                            MediaSection(
                                title = stringResource(id = R.string.label_comics),
                                items = character.comics.items,
                                mediaType = MediaType.COMICS
                            )
                        if (character.series.items.isNotEmpty())
                            MediaSection(
                                title = stringResource(id = R.string.label_series),
                                items = character.series.items,
                                mediaType = MediaType.SERIES
                            )
                        if (character.stories.items.isNotEmpty())
                            MediaSection(
                                title = stringResource(id = R.string.label_stories),
                                items = character.stories.items,
                                mediaType = MediaType.STORIES
                            )
                        if (character.events.items.isNotEmpty())
                            MediaSection(
                                title = stringResource(id = R.string.label_events),
                                items = character.events.items,
                                mediaType = MediaType.EVENTS
                            )
                    }
                }

            }
        }
    }
}