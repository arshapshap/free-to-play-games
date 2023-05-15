package com.example.games.di

import androidx.fragment.app.ListFragment
import com.example.games.data.di.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppBindsModule::class, DataModule::class]
)
interface AppComponent {

    fun inject(listFragment: ListFragment)
}