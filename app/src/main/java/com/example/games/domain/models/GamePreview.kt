package com.example.games.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList
import java.util.*

@Immutable
data class GamePreview(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val shortDescription: String,
    val gameUrl: String,
    val genre: String,
    val platforms: PersistentList<Platform>,
    val publisher: String,
    val developer: String,
    val releaseDate: Date
)