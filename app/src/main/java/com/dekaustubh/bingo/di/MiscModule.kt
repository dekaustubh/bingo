package com.dekaustubh.bingo.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MiscModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}