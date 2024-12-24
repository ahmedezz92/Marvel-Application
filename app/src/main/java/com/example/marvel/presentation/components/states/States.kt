package com.example.marvel.presentation.components.states

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(20.dp),
            color = Color.Red
        )
    }

}

@Composable
fun ErrorState(error: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)

    ) {
        Text(
            text = error,
            textAlign = TextAlign.Center,
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun EmptyState(empty: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(50.dp)
    ) {
        Text(
            text = empty,
            textAlign = TextAlign.Center,
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}