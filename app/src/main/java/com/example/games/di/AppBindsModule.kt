package com.example.games.di

import com.example.games.data.repositories.GamesRepositoryImpl
import com.example.games.domain.repositories.GamesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class AppBindsModule {

    @Binds
    abstract fun bindsGamesRepositoryImpl(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository
}