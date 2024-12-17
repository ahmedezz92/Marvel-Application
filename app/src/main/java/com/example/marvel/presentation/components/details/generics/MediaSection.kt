package com.example.marvel.presentation.components.details.generics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.marvel.domain.model.Thumbnail
import com.example.marvel.presentation.components.details.CharacterDetailsViewModel

@Composable
fun MediaSection(
    title: String,
    items: List<MediaItem>,
    mediaType: MediaType,
    viewModel: CharacterDetailsViewModel = hiltViewModel()
) {
    var selectedMedia by remember { mutableStateOf<Pair<String, Thumbnail>?>(null) }
    val mediaState by viewModel.mediaState.collectAsState()
    val loadingStates by viewModel.loadingStates.collectAsState()

    // Debug print
    val defaultThumbnail = Thumbnail(
        path = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available",
        extension = "jpg"
    )
    LaunchedEffect(items) {
        viewModel.loadMediaItems(items, mediaType)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        when {

//            loadingStates[mediaType] == true -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
            items.isNotEmpty() -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(mediaState[mediaType] ?: emptyList()) {
                        val thumbnail = it.thumbnail ?: defaultThumbnail

                        MediaCard(
                            name = it.title,
                            thumbnail = thumbnail,
                            onPreviewClick = {
                                selectedMedia = it.title to thumbnail
                            }
                        )
                    }
                }
            }

            mediaState[mediaType].isNullOrEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No $title available",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

//            items.isNotEmpty() -> {
//                LazyRow(
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    items(items) { item ->
//                        // Find corresponding media details if loaded, otherwise use original item
//                        val mediaDetails = mediaState[mediaType]
//                            ?.find { it.title == item.name }
//
//                        val thumbnail = mediaDetails?.thumbnail ?:
//                        Thumbnail(path = "", extension = "")
//
//                        MediaCard(
//                            name = item.name,
//                            thumbnail = thumbnail,
//                            isLoading = loadingStates[mediaType] ?: true,
//                            onPreviewClick = {
//                                selectedMedia = item.name to thumbnail
//                            }
//                        )
//                    }
//                }
//            }

        }
    }
    selectedMedia?.let { (name, thumbnail) ->
        Dialog(
            onDismissRequest = { selectedMedia = null },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.9f))
            ) {
                IconButton(
                    onClick = { selectedMedia = null },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .background(Color.Black.copy(0.3f), MaterialTheme.shapes.small)
                        .zIndex(1f)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close preview",
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = "${thumbnail.path}.${thumbnail.extension}",
                        contentDescription = name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp),
                        contentScale = ContentScale.Fit
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.6f))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

