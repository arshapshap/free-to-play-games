package com.example.games.data.repositories

import com.example.games.data.mappers.toDomain
import com.example.games.data.network.GamesApiService
import com.example.games.domain.models.Game
import com.example.games.domain.repositories.GamesRepository

class GamesRepositoryImpl(private val remoteSource: GamesApiService) : GamesRepository {

    override suspend fun getGamesList(): List<Game> {
        return remoteSource.getGames().map {gameResponse ->
            gameResponse.toDomain()
        }
    }

    override suspend fun getGame(id: Int): Game {
        return remoteSource.getGame(id).toDomain()
    }
}