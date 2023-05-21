package com.example.games.presentation.di

import com.example.games.domain.usecases.GetGameUseCase
import com.example.games.domain.usecases.GetGamesListUseCase
import com.example.games.presentation.screens.gameScreen.GameScreenViewModel
import com.example.games.presentation.screens.listScreen.ListScreenViewModel
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideListScreenViewModel(getGamesListUseCase: GetGamesListUseCase): ListScreenViewModel =
        ListScreenViewModel(getGamesListUseCase = getGamesListUseCase)

    @Provides
    fun provideGameScreenViewModel(getGameUseCase: GetGameUseCase): GameScreenViewModel =
        GameScreenViewModel(getGameUseCase = getGameUseCase)
}