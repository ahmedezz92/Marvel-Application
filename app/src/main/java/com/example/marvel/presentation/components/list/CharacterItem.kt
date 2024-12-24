package com.example.marvel.presentation.components.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.marvel.R
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
        Row(
            modifier = Modifier
                .padding(top = 80.dp)
                .align(Alignment.BottomStart),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(15.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_rectangle_angeled),
                    contentDescription = null,
                )
                Text(
                    text = character.name!!,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.widthIn(min = 50.dp, max = 150.dp)
                )
            }
        }
    }
}