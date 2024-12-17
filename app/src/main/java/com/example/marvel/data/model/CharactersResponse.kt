package com.example.marvel.data.model

import com.example.marvel.domain.model.Character

data class CharactersResponse(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Character>
)