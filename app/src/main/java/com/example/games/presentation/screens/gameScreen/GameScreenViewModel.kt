package com.example.games.presentation.screens.gameScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.games.domain.models.Game
import com.example.games.domain.usecases.GetGameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GameScreenViewState(
    val game: Game? = null
)

sealed interface GameScreenEvent {
    data class OnStart(val gameId: Int) : GameScreenEvent
}

class GameScreenViewModel @Inject constructor(
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
//        val game = Game(
//            452,
//            "Call Of Duty: Warzone ($gameId)",
//            "https://www.freetogame.com/g/452/thumbnail.jpg",
//            "Live",
//            "A standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare.",
//            "\tCall of Duty: Warzone is both a standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare. Warzone features two modes — the general 150-player battle royle, and “Plunder”. The latter mode is described as a “race to deposit the most Cash”. In both modes players can both earn and loot cash to be used when purchasing in-match equipment, field upgrades, and more. Both cash and XP are earned in a variety of ways, including completing contracts.\n\tAn interesting feature of the game is one that allows players who have been killed in a match to rejoin it by winning a 1v1 match against other felled players in the Gulag.\n\tOf course, being a battle royale, the game does offer a battle pass. The pass offers players new weapons, playable characters, Call of Duty points, blueprints, and more. Players can also earn plenty of new items by completing objectives offered with the pass.",
//            "https://www.freetogame.com/open/call-of-duty-warzone",
//            "Shooter",
//            persistentListOf(Platform.Windows),
//            "Activision",
//            "Infinity Ward",
//            SimpleDateFormat("yyyy-MM-dd").parse("2020-03-10") ?: Date(),
//            true,
//            MinimumSystemRequirements(
//                "Windows 7 64-Bit (SP1) or Windows 10 64-Bit",
//                "Intel Core i3-4340 or AMD FX-6300",
//                "8GB RAM",
//                "NVIDIA GeForce GTX 670 / GeForce GTX 1650 or Radeon HD 7950",
//                "175GB HD space"
//            ),
//            persistentListOf(
//                Screenshot(0, "https://www.freetogame.com/g/453/Gotham-City-Impostors-1.jpg"),
//                Screenshot(0, "https://www.freetogame.com/g/453/Gotham-City-Impostors-1.jpg"),
//                Screenshot(0, "https://www.freetogame.com/g/453/Gotham-City-Impostors-1.jpg"),
//                Screenshot(0, "https://www.freetogame.com/g/453/Gotham-City-Impostors-1.jpg"),
//            )
//        )

        viewModelScope.launch {
            val game = getGameUseCase.execute(gameId)
            _state.emit(
                _state.value.copy(
                    game = game
                )
            )

        }
    }
}