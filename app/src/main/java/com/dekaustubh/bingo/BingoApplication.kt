package com.dekaustubh.bingo

import com.dekaustubh.bingo.di.ApplicationComponent
import com.dekaustubh.bingo.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BingoApplication : DaggerApplication() {
    lateinit var applicationComponent : ApplicationComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        applicationComponent = DaggerApplicationComponent.builder()
            .application(this)
            .build()
        return applicationComponent
    }

}