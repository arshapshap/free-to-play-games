package com.example.games.presentation.screens.listScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.games.R
import com.example.games.domain.models.GamePreview
import com.example.games.domain.models.Platform
import com.example.games.presentation.ui.theme.GamesTheme
import com.example.games.presentation.ui.theme.Typography
import com.example.games.presentation.utils.getDrawable
import com.example.games.presentation.utils.toCalendar
import kotlinx.collections.immutable.persistentListOf
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun GameListItem(
    gamePreview: GamePreview,
    onClick: (GamePreview) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onClick.invoke(gamePreview) },
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ) {
        Row {
            Thumbnail(gamePreview = gamePreview)
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                MainInfo(gamePreview = gamePreview)
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                BottomInfo(gamePreview = gamePreview)
            }
        }
    }
}

@Composable
fun Thumbnail(gamePreview: GamePreview) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(
                fraction = 0.4f
            ),
        contentAlignment = Alignment.Center
    ) {
        val painter = rememberAsyncImagePainter(model = gamePreview.thumbnail)
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(10.dp))
        )
        if (painter.state !is AsyncImagePainter.State.Success) {
            Icon(
                painter = painterResource(id = R.drawable.ic_puzzle),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Composable
fun MainInfo(gamePreview: GamePreview) {
    Text(
        text = gamePreview.title,
        style = Typography.h6,
    )
    Text(
        text = gamePreview.shortDescription,
        style = Typography.caption,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun BottomInfo(gamePreview: GamePreview) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${gamePreview.genre}, ${gamePreview.releaseDate.toCalendar().get(Calendar.YEAR)}",
            modifier = Modifier
                .padding(end = 5.dp),
            color = Color.Gray,
            style = Typography.overline
        )
        Platforms(
            gamePreview = gamePreview
        )
    }
}

@Composable
fun Platforms(gamePreview: GamePreview) {
    gamePreview.platforms.forEach {platform ->
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
fun GameListItemPreview() {
    val gamePreview = GamePreview(
        452,
        "Call Of Duty: Warzone",
        "https://www.freetogame.com/g/452/thumbnail.jpg",
        "A standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare. A standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare.",
        "https://www.freetogame.com/open/call-of-duty-warzone",
        "Shooter",
        persistentListOf(Platform.Windows, Platform.WebBrowser),
        "Activision",
        "Infinity Ward",
        SimpleDateFormat("yyyy-MM-dd").parse("2020-03-10") ?: Date(),
    )
    GamesTheme {
        GameListItem(gamePreview = gamePreview, onClick = {})
    }
}