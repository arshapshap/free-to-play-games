package com.example.games.domain.models

data class MinimumSystemRequirements(
    val os: String,
    val processor: String,
    val memory: String,
    val graphics: String,
    val storage: String
)