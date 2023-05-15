package com.example.games.domain.repositories

import com.example.games.domain.models.Game

interface GamesRepository {

   suspend fun getGamesList(): List<Game>

   suspend fun getGame(id: Int): Game
}