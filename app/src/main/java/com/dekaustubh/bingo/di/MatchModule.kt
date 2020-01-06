package com.dekaustubh.bingo.di

import com.dekaustubh.bingo.match.StartMatchContract
import com.dekaustubh.bingo.match.StartMatchPresenterImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MatchModule {
    @Binds
    abstract fun bindStartMatchPresenter(startMatchPresenter: StartMatchPresenterImpl): StartMatchContract.Presenter
}