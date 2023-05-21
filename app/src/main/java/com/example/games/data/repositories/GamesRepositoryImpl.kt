package com.example.games.data.repositories

import com.example.games.data.mappers.toDomain
import com.example.games.data.network.GamesApiService
import com.example.games.domain.models.Game
import com.example.games.domain.models.GamePreview
import com.example.games.domain.repositories.GamesRepository
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(private val remoteSource: GamesApiService) : GamesRepository {

    override suspend fun getGamesList(): List<GamePreview> {
        return remoteSource.getGames().map {gamePreviewResponse ->
            gamePreviewResponse.toDomain()
        }
    }

    override suspend fun getGame(id: Int): Game {
        return remoteSource.getGame(id).toDomain()
    }
}