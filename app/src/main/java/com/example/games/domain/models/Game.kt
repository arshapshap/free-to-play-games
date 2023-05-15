package com.example.games.domain.models

data class Game(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val status: String,
    val shortDescription: String,
    val description: String,
    val gameUrl: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val releaseDate: String,
    val minimumSystemRequirements: MinimumSystemRequirements,
    val screenshots: List<Screenshot>
)

