package com.example.marvel.domain.model

import com.example.marvel.presentation.components.details.generics.MediaItem

data class Series(
    val available: Int,
    val collectionURI: String,
    val returned: Int,
    val items: List<SeriesItems>
)

data class SeriesItems(
    override val resourceURI: String,
    val name: String
) : MediaItem