package com.example.games.presentation.screens.listScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.games.domain.models.GamePreview
import com.example.games.domain.usecases.GetGamesListUseCase
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListScreenViewState(
    val games: PersistentList<GamePreview> = persistentListOf()
)

sealed interface ListScreenEvent {
    object OnStart : ListScreenEvent
    data class OnListItemClickScreen(val gameId: Int): ListScreenEvent
}

sealed interface ListScreenAction {
    data class OpenGameScreen(val gameId: Int) : ListScreenAction
}

class ListScreenViewModel @Inject constructor(
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
//        val gamePreview = GamePreview(
//            1,
//            "Call Of Duty: Warzone",
//            "https://www.freetogame.com/g/452/thumbnail.jpg",
//            "A standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare. A standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare.",
//            "https://www.freetogame.com/open/call-of-duty-warzone",
//            "Shooter",
//            persistentListOf(Platform.Windows, Platform.WebBrowser),
//            "Activision",
//            "Infinity Ward",
//            SimpleDateFormat("yyyy-MM-dd").parse("2020-03-10") ?: Date(),
//        )


        viewModelScope.launch {
            val games = getGamesListUseCase.execute().toPersistentList()
            _state.emit(
                _state.value.copy(
                    games = games
                )
            )
        }
    }

    private fun onListItemClick(gameId: Int) {
        viewModelScope.launch {
            _action.emit(ListScreenAction.OpenGameScreen(gameId = gameId))
        }
    }
}