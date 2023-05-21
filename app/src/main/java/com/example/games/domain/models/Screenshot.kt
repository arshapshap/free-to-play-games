package com.example.games.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class Screenshot(
    val id: Int,
    val image: String
)