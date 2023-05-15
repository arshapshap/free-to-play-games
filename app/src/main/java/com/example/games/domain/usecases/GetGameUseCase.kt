package com.example.games.domain.usecases

import com.example.games.domain.models.Game
import com.example.games.domain.repositories.GamesRepository
import javax.inject.Inject

class GetGameUseCase @Inject constructor(private val repository: GamesRepository) {

    suspend fun execute(id: Int): Game {
        return repository.getGame(id)
    }
}