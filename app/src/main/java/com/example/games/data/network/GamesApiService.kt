package com.example.games.data.network

import com.example.games.data.network.response.GameResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApiService {

    @GET("games")
    suspend fun getGames(): List<GameResponse>

    @GET("game")
    suspend fun getGame(@Query("id") id: Int): GameResponse
}