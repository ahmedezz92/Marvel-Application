package com.example.marvel.domain.model

data class Character(
    val id: Int,
    val name: String?,
    val title:String?,
    val description: String,
    val modified: String,
    val resourceURI: String,
    val thumbnail: Thumbnail,
    val comics: Comic,
    val series: Series,
    val stories: Stories,
    val events: Events,
)