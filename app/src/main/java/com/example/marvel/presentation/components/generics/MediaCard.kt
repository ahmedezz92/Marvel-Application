package com.example.marvel.presentation.components.generics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.marvel.domain.model.Thumbnail
import com.example.marvel.presentation.components.states.LoadingState

@Composable
fun MediaCard(
    name: String,
    thumbnail: Thumbnail,
    onPreviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imageURL = "${thumbnail.path}.${thumbnail.extension}"
    Card(
        modifier = modifier
            .width(120.dp)
            .clickable(onClick = onPreviewClick)
            .padding(end = 5.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column {
//            AsyncImage(
//                model = imageURL,
//                contentDescription = "Media cover",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp),
//                contentScale = ContentScale.Crop
//            )

            SubcomposeAsyncImage(
                model = imageURL,
                contentDescription = "Media cover",
                modifier = Modifier
                    .width(120.dp)
                    .height(160.dp),
                contentScale = ContentScale.FillBounds,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingState()
                    }
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Failed to load image",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(5.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center

                )
            }
        }
    }
}