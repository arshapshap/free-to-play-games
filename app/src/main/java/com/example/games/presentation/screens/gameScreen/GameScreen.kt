package com.example.games.presentation.screens.gameScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.games.domain.models.Game
import com.example.games.domain.models.MinimumSystemRequirements
import com.example.games.domain.models.Platform
import com.example.games.domain.models.Screenshot
import com.example.games.presentation.ui.theme.Typography
import com.example.games.presentation.utils.getDrawable
import com.example.games.presentation.utils.toCalendar
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun GameScreen(
    navController: NavController,
    gameId: Int?,
    viewModel: GameScreenViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    if (gameId != null)
        viewModel::event.invoke(GameScreenEvent.OnStart(gameId = gameId))

    GameScreenContent(
        viewState = state.value,
        eventHandler = viewModel::event
    )
}

@Composable
private fun GameScreenContent(
    viewState: GameScreenViewState,
    eventHandler: (GameScreenEvent) -> Unit
) {
    if (viewState.game == null)
        return
    Card(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = rememberScrollState()
            )
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = 20.dp
                )
        ) {
            HeadInfo(
                game = viewState.game
            )
            Spacer(
                modifier = Modifier
                    .padding(5.dp)
            )
            MainInfo(
                game = viewState.game
            )
        }
    }
}

@Composable
private fun MainInfo(game: Game) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = 20.dp
            )
    ) {
        Text(
            text = "\r ${game.description}"
        )
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        Parameters(
            game = game
        )
        if (game.hasMinimumSystemRequirements) {
            Spacer(
                modifier = Modifier
                    .padding(10.dp)
            )
            SystemRequirements(
                minimumSystemRequirements = game.minimumSystemRequirements
            )
        }
        if (!game.screenshots.isEmpty()) {
            Spacer(
                modifier = Modifier
                    .padding(10.dp)
            )
            Screenshots(
                screenshots = game.screenshots
            )
        }
    }
}

@Composable
private fun Screenshots(screenshots: PersistentList<Screenshot>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Screenshots:",
            style = Typography.subtitle1,
            textAlign = TextAlign.Center
        )
        screenshots.forEach { screenshot ->
            Spacer(
                modifier = Modifier
                    .padding(5.dp)
            )
            AsyncImage(
                model = screenshot.image,
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
            )
        }
    }
}

@Composable
private fun HeadInfo(game: Game) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Thumbnail(
            game = game
        )
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        Text(
            text = game.title,
            style = Typography.h5,
            textAlign = TextAlign.Center
        )
        SubtitleInfo(
            game = game
        )
    }
}

@Composable
private fun Thumbnail(game: Game) {
    AsyncImage(
        model = game.thumbnail,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(10.dp))
    )
}

@Composable
private fun Parameters(game: Game) {
    Parameter(
        name = "Status",
        value = game.status
    )
    Parameter(
        name = "Publisher",
        value = game.publisher
    )
    Parameter(
        name = "Developer",
        value = game.developer
    )
    Parameter(
        name = "Release date",
        value = SimpleDateFormat("dd MMMM yyyy", Locale.US).format(game.releaseDate)
    )
}

@Composable
private fun SystemRequirements(minimumSystemRequirements: MinimumSystemRequirements) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Minimum system requirements:",
            style = Typography.subtitle1,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .padding(5.dp)
        )
        val params = persistentMapOf(
            "OS" to minimumSystemRequirements.os!!,
            "Processor" to minimumSystemRequirements.processor!!,
            "Memory" to minimumSystemRequirements.memory!!,
            "Graphics" to minimumSystemRequirements.graphics!!,
            "Storage" to minimumSystemRequirements.storage!!
        )
        params.forEach {
            ParameterLong(
                name = it.key,
                value = it.value
            )
        }
    }
}

@Composable
private fun Parameter(name: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "· $name: ",
            style = Typography.caption
        )
        Text(
            text = value,
            style = Typography.caption,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun ParameterLong(name: String, value: String) {
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.Gray
            )
            .padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$name: ",
                style = Typography.overline
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = value,
                style = Typography.caption,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun SubtitleInfo(game: Game) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${game.genre}, ${game.releaseDate.toCalendar().get(Calendar.YEAR)}",
            modifier = Modifier
                .padding(end = 5.dp),
            color = Color.Gray,
            style = Typography.overline
        )
        Platforms(
            game = game
        )
    }
}

@Composable
private fun Platforms(game: Game) {
    game.platforms.forEach { platform ->
        Image(
            painter = painterResource(
                id = platform.getDrawable()),
            modifier = Modifier
                .size(20.dp),
            contentDescription = platform.name,
            colorFilter = ColorFilter.tint(Color.Gray)
        )
        Spacer(
            modifier = Modifier
                .padding(2.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GameScreenPreview() {
    val game = Game(
        452,
        "Call Of Duty: Warzone",
        "https://www.freetogame.com/g/452/thumbnail.jpg",
        "Live",
        "A standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare.",
        "\tCall of Duty: Warzone is both a standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare. Warzone features two modes — the general 150-player battle royle, and “Plunder”. The latter mode is described as a “race to deposit the most Cash”. In both modes players can both earn and loot cash to be used when purchasing in-match equipment, field upgrades, and more. Both cash and XP are earned in a variety of ways, including completing contracts.\n\tAn interesting feature of the game is one that allows players who have been killed in a match to rejoin it by winning a 1v1 match against other felled players in the Gulag.\n\tOf course, being a battle royale, the game does offer a battle pass. The pass offers players new weapons, playable characters, Call of Duty points, blueprints, and more. Players can also earn plenty of new items by completing objectives offered with the pass.",
        "https://www.freetogame.com/open/call-of-duty-warzone",
        "Shooter",
        persistentListOf(Platform.Windows),
        "Activision",
        "Infinity Ward",
        SimpleDateFormat("yyyy-MM-dd").parse("2020-03-10") ?: Date(),
        true,
        MinimumSystemRequirements(
            "Windows 7 64-Bit (SP1) or Windows 10 64-Bit",
            "Intel Core i3-4340 or AMD FX-6300",
            "8GB RAM",
            "NVIDIA GeForce GTX 670 / GeForce GTX 1650 or Radeon HD 7950",
            "175GB HD space"
        ),
        persistentListOf(
            Screenshot(0, "https://www.freetogame.com/g/453/Gotham-City-Impostors-1.jpg"),
            Screenshot(0, "https://www.freetogame.com/g/453/Gotham-City-Impostors-1.jpg"),
            Screenshot(0, "https://www.freetogame.com/g/453/Gotham-City-Impostors-1.jpg"),
            Screenshot(0, "https://www.freetogame.com/g/453/Gotham-City-Impostors-1.jpg"),
        )
    )

    val state = GameScreenViewState(
        game = game
    )

    GameScreenContent(
        viewState = state,
        eventHandler = {}
    )
}