package com.example.marvel.presentation.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.marvel.domain.model.Character

@Composable
fun CharacterItem(
    character: Character,
    onCharacterClick: (characterId: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp)
            .clickable { onCharacterClick(character.id) },
    ) {
        val imageURL = character.thumbnail.path.plus(".".plus(character.thumbnail.extension))
        AsyncImage(
            model = imageURL,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterStart)
                .wrapContentWidth()
                .background(Color.White)
                .padding(16.dp),

            ) {
            Text(
                text = character.name!!,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}