package com.example.games.domain.repositories

import com.example.games.domain.models.Game
import com.example.games.domain.models.GamePreview

interface GamesRepository {

   suspend fun getGamesList(): List<GamePreview>

   suspend fun getGame(id: Int): Game
}