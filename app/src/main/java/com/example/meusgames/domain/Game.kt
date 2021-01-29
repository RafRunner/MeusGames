package com.example.meusgames.domain

import java.io.Serializable

data class Game(
    val name: String,
    val imageId: String,
    val imageUrl: String,
    val releaseDate: String,
) : Serializable