package com.example.games.data.mappers

import com.example.games.data.network.response.GameResponse
import com.example.games.domain.models.Game
import com.example.games.domain.models.MinimumSystemRequirements
import com.example.games.domain.models.Screenshot

fun GameResponse.toDomain(): Game {
    return Game(
        id = id ?: 0,
        title = title ?: "",
        thumbnail = thumbnail ?: "",
        status = status ?: "",
        shortDescription = shortDescription ?: "",
        description = description ?: "",
        gameUrl = gameUrl ?: "",
        genre = genre ?: "",
        platform = platform ?: "",
        publisher = publisher ?: "",
        developer = developer ?: "",
        releaseDate = releaseDate ?: "",
        minimumSystemRequirements = MinimumSystemRequirements(
            os = minimumSystemRequirements?.os ?: "",
            processor = minimumSystemRequirements?.processor ?: "",
            memory = minimumSystemRequirements?.memory ?: "",
            graphics = minimumSystemRequirements?.graphics ?: "",
            storage = minimumSystemRequirements?.storage ?: ""
        ),
        screenshots = screenshots?.map {screenshotResponse ->
            Screenshot(
                id = screenshotResponse?.id ?: 0,
                image = screenshotResponse?.image ?: ""
            )
        } ?: listOf()
    )
}