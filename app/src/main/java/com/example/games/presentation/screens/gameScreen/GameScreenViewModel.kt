package com.example.games.presentation.screens.gameScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.games.domain.models.Game
import com.example.games.domain.usecases.GetGameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GameScreenViewState(
    val game: Game? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
)

sealed interface GameScreenEvent {
    data class OnStart(val gameId: Int) : GameScreenEvent
}

class GameScreenViewModel(
    private val getGameUseCase: GetGameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(GameScreenViewState())
    val state: StateFlow<GameScreenViewState>
        get() = _state.asStateFlow()

    fun event(gameScreenEvent: GameScreenEvent) {
        when (gameScreenEvent) {
            is GameScreenEvent.OnStart -> onStart(gameScreenEvent.gameId)
        }
    }

    private fun onStart(gameId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                val game = getGameUseCase.execute(gameId)
                _state.emit(
                    _state.value.copy(
                        game = game,
                        isLoading = false
                    )
                )
            }.onFailure {
                _state.emit(
                    _state.value.copy(
                        isLoading = false,
                        isError = true
                    )
                )
            }
        }
    }
}