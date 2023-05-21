package com.example.games.domain.usecases

import com.example.games.domain.models.GamePreview
import com.example.games.domain.repositories.GamesRepository
import javax.inject.Inject

class GetGamesListUseCase @Inject constructor(private val repository: GamesRepository) {

    suspend fun execute(): List<GamePreview> {
        return repository.getGamesList()
    }
}