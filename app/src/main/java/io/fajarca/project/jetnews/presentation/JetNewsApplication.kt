package io.fajarca.project.jetnews.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JetNewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}