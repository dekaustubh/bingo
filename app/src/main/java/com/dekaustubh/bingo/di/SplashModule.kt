package com.dekaustubh.bingo.di

import com.dekaustubh.bingo.splash.SplashContract
import com.dekaustubh.bingo.splash.SplashPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class SplashModule {
    @Binds
    abstract fun provideSplashPresenter(splashPresenter: SplashPresenter): SplashContract.Presenter
}