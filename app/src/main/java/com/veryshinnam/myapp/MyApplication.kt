package com.veryshinnam.myapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        com.jakewharton.threetenabp.AndroidThreeTen.init(this)
    }
}