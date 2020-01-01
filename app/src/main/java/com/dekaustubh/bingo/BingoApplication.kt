package com.dekaustubh.bingo

import com.dekaustubh.bingo.di.ApplicationComponent
import com.dekaustubh.bingo.di.DaggerApplicationComponent
import com.dekaustubh.bingo.di.DatabaseModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class BingoApplication : DaggerApplication() {
    private lateinit var applicationComponent : ApplicationComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        applicationComponent = DaggerApplicationComponent.builder()
            .application(this)
            .databaseModule(DatabaseModule())
            .build()
        return applicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}