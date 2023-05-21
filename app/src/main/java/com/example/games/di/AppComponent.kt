package com.example.games.di

import com.example.games.data.di.DataModule
import com.example.games.presentation.di.PresentationModule
import com.example.games.presentation.screens.gameScreen.GameScreenViewModel
import com.example.games.presentation.screens.listScreen.ListScreenViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppBindsModule::class, DataModule::class, PresentationModule::class]
)
interface AppComponent {

    fun listScreenViewModel(): ListScreenViewModel

    fun gameScreenViewModel(): GameScreenViewModel
}