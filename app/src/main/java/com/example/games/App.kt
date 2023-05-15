package com.example.games

import android.app.Application
import com.example.games.di.AppComponent
import com.example.games.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }
}