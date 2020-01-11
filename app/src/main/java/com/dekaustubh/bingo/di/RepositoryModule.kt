package com.dekaustubh.bingo.di

import com.dekaustubh.bingo.common.UserRepository
import com.dekaustubh.bingo.common.UserRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}