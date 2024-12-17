package com.example.marvel.presentation.components.generics

import com.example.marvel.domain.model.Thumbnail

data class MediaDetails(
    val id: Int,
    val title: String,
    val description: String?,
    val thumbnail: Thumbnail?
)
