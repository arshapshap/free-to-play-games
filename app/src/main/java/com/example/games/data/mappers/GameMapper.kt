package com.example.games.data.mappers

import com.example.games.data.network.response.GamePreviewResponse
import com.example.games.data.network.response.GameResponse
import com.example.games.data.network.response.MinimumSystemRequirementsResponse
import com.example.games.domain.models.*
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import java.text.SimpleDateFormat
import java.util.*

fun GameResponse.toDomain(): Game {
    return Game(
        id = id ?: 0,
        title = title ?: "",
        thumbnail = thumbnail ?: "",
        status = status ?: "",
        shortDescription = shortDescription ?: "",
        description = formatText(description),
        gameUrl = gameUrl ?: "",
        genre = genre ?: "",
        platforms = getPlatformsFromString(platform),
        publisher = publisher ?: "",
        developer = developer ?: "",
        releaseDate = getDateFromString(releaseDate),
        hasMinimumSystemRequirements = checkIfNull(minimumSystemRequirements),
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
        }?.toPersistentList() ?: persistentListOf()
    )
}

fun GamePreviewResponse.toDomain(): GamePreview {
    return GamePreview(
        id = id ?: 0,
        title = title ?: "",
        thumbnail = thumbnail ?: "",
        shortDescription = shortDescription ?: "",
        gameUrl = gameUrl ?: "",
        genre = genre ?: "",
        platforms = getPlatformsFromString(platform ?: ""),
        publisher = publisher ?: "",
        developer = developer ?: "",
        releaseDate = getDateFromString(releaseDate ?: ""),
    )
}

private fun formatText(string: String?): String {
    return "\t${string?.replace("\r\n\r\n", "\n\t")}"
}

private fun checkIfNull(minimumSystemRequirements: MinimumSystemRequirementsResponse?): Boolean {
    return listOf(minimumSystemRequirements?.os, minimumSystemRequirements?.processor, minimumSystemRequirements?.memory, minimumSystemRequirements?.graphics, minimumSystemRequirements?.storage)
        .all { s -> s != null }
}

private fun getPlatformsFromString(string: String?): PersistentList<Platform> {
    if (string == null)
        return persistentListOf()

    val list = arrayListOf<Platform>()
    if ("Windows" in string)
        list.add(Platform.Windows)
    if ("Web Browser" in string)
        list.add(Platform.WebBrowser)

    return list.toPersistentList()
}

private fun getDateFromString(string: String?): Date {
    return SimpleDateFormat("yyyy-MM-dd").parse(string ?: "") ?: Date()
}