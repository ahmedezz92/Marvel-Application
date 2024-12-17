package com.example.marvel.domain.model

import com.example.marvel.presentation.components.generics.MediaItem

data class Events(
    val available: String,
    val collectionURI: String,
    val items: List<EventsItems>
)

data class EventsItems(
    override val resourceURI: String,
    val name: String
) : MediaItem