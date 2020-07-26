package com.and.newton.summercamp

import android.app.Application
import com.and.newton.common.utils.AppPreferences
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class SummerCampApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
        AppPreferences.init(this)
    }
}