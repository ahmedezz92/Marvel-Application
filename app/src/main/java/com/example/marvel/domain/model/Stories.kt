package com.example.marvel.domain.model

import com.example.marvel.presentation.components.details.generics.MediaItem

data class Stories(
    val available: Int,
    val collectionURI: String,
    val returned: Int,
    val items: List<StoriesItems>
)

data class StoriesItems(
    override val resourceURI: String,
    val name: String
) : MediaItem
