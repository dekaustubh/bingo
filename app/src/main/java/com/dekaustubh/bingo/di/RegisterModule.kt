package com.dekaustubh.bingo.di

import com.dekaustubh.bingo.register.RegisterContract
import com.dekaustubh.bingo.register.RegisterPresenterImpl
import com.dekaustubh.bingo.register.RegisterRepository
import com.dekaustubh.bingo.register.RegisterRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RegisterModule {
    @Binds
    abstract fun provideRegisterPresenter(registerPresenterImpl: RegisterPresenterImpl): RegisterContract.RegisterPresenter

    @Binds
    abstract fun provideRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl): RegisterRepository
}