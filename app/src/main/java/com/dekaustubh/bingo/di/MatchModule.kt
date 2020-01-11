package com.dekaustubh.bingo.di

import com.dekaustubh.bingo.match.create.CreateMatchContract
import com.dekaustubh.bingo.match.create.StartMatchPresenterImpl
import com.dekaustubh.bingo.match.join.JoinMatchContract
import com.dekaustubh.bingo.match.join.JoinMatchPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class MatchModule {
    @Binds
    abstract fun bindStartMatchPresenter(startMatchPresenter: StartMatchPresenterImpl): CreateMatchContract.Presenter

    @Binds
    abstract fun bindJoinMatchPresenter(joinMatchPresenter: JoinMatchPresenter): JoinMatchContract.Presenter
}