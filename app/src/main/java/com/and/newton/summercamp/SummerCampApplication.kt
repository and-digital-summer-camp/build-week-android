package com.and.newton.summercamp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class SummerCampApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}