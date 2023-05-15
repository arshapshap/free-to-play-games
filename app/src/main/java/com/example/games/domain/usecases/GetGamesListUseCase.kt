package com.example.games.domain.usecases

import com.example.games.domain.models.Game
import com.example.games.domain.repositories.GamesRepository
import javax.inject.Inject

class GetGamesListUseCase @Inject constructor(private val repository: GamesRepository) {

    suspend fun execute(): List<Game> {
        return repository.getGamesList()
    }
}