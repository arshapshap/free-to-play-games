package com.example.games.presentation.screens.listScreen

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.games.domain.models.GamePreview
import com.example.games.domain.usecases.GetGamesListUseCase
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Immutable
data class ListScreenViewState(
    val games: PersistentList<GamePreview> = persistentListOf(),
    val isLoading: Boolean = true,
    val isError: Boolean = false,
)

sealed interface ListScreenEvent {
    object OnStart : ListScreenEvent
    data class OnListItemClickScreen(val gameId: Int): ListScreenEvent
}

sealed interface ListScreenAction {
    data class OpenGameScreen(val gameId: Int) : ListScreenAction
}

class ListScreenViewModel(
    private val getGamesListUseCase: GetGamesListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListScreenViewState())
    val state: StateFlow<ListScreenViewState>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<ListScreenAction?>()
    val action: SharedFlow<ListScreenAction?>
        get() = _action.asSharedFlow()

    fun event(listScreenEvent: ListScreenEvent) {
        when (listScreenEvent) {
            is ListScreenEvent.OnStart -> onStart()
            is ListScreenEvent.OnListItemClickScreen -> onListItemClick(listScreenEvent.gameId)
        }
    }

    private fun onStart() {
        viewModelScope.launch {
            kotlin.runCatching {
                val games = getGamesListUseCase.execute()
                _state.emit(
                    _state.value.copy(
                        games = games.toPersistentList(),
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

    private fun onListItemClick(gameId: Int) {
        viewModelScope.launch {
            _action.emit(ListScreenAction.OpenGameScreen(gameId = gameId))
        }
    }
}