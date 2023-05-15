package com.example.games.data.network.response

import com.google.gson.annotations.SerializedName

data class ScreenshotResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?
)