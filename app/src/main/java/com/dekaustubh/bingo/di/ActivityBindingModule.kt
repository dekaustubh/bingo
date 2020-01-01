package com.dekaustubh.bingo.di

import com.dekaustubh.bingo.main.MainActivity
import com.dekaustubh.bingo.register.RegisterActivity
import com.dekaustubh.bingo.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector()
    abstract fun contributeRegisterActivity(): RegisterActivity
}