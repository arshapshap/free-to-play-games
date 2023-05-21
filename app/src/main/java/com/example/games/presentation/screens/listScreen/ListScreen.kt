package com.example.games.presentation.screens.listScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.commandiron.compose_loading.FadingCircle
import com.example.games.domain.models.GamePreview
import com.example.games.domain.models.Platform
import com.example.games.presentation.Screen
import com.example.games.presentation.elements.ErrorBox
import kotlinx.collections.immutable.persistentListOf
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListScreenViewModel = viewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action = viewModel.action.collectAsStateWithLifecycle(null)

    ListScreenContent(
        viewState = state.value,
        eventHandler = viewModel::event
    )
    ListScreenActions(
        viewAction = action.value,
        navController = navController
    )
}

@Composable
fun ListScreenContent(
    viewState: ListScreenViewState,
    eventHandler: (ListScreenEvent) -> Unit
) {
    eventHandler.invoke(ListScreenEvent.OnStart)

    if (viewState.isError) {
        ErrorBox()
        return
    }
    if (viewState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            FadingCircle()
        }
        return
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        GamesList(
            viewState = viewState,
            eventHandler = eventHandler
        )
    }
}

@Composable
private fun GamesList(
    viewState: ListScreenViewState,
    eventHandler: (ListScreenEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(
                horizontal = 10.dp
            )
    ) {
        item {
            Spacer(
                modifier = Modifier.padding(5.dp)
            )
        }
        items(items = viewState.games) { gamePreview ->
            GameListItem(
                gamePreview = gamePreview,
                onClick = {
                    eventHandler.invoke(ListScreenEvent.OnListItemClickScreen(it.id))
                }
            )
            Spacer(
                modifier = Modifier
                    .padding(5.dp)
            )
        }
        item {
            Spacer(
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
private fun ListScreenActions(
    navController: NavController,
    viewAction: ListScreenAction?
) {
    LaunchedEffect(viewAction) {
        when (viewAction) {
            null -> Unit
            is ListScreenAction.OpenGameScreen -> {
                navController.navigate(Screen.Game(viewAction.gameId).route)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GamesListPreview() {
    val gamePreview = GamePreview(
        452,
        "Call Of Duty: Warzone",
        "https://www.freetogame.com/g/452/thumbnail.jpg",
        "A standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare. A standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare.",
        "https://www.freetogame.com/open/call-of-duty-warzone",
        "Shooter",
        persistentListOf(Platform.Windows),
        "Activision",
        "Infinity Ward",
        SimpleDateFormat("yyyy-MM-dd").parse("2020-03-10") ?: Date(),
    )
    val games = persistentListOf(gamePreview, gamePreview, gamePreview)
    val state = ListScreenViewState(
        games = games
    )
    ListScreenContent(
        viewState = state,
        eventHandler = {}
    )
}