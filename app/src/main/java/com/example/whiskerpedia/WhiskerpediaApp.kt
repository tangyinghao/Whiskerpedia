package com.example.whiskerpedia

import android.app.Application
import com.example.whiskerpedia.database.AppContainer
import com.example.whiskerpedia.database.DefaultAppContainer

class WhiskerpediaApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}