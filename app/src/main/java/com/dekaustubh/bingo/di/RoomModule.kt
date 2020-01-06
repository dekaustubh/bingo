package com.dekaustubh.bingo.di

import com.dekaustubh.bingo.register.FetchRoomPresenter
import com.dekaustubh.bingo.register.FetchRoomsContract
import com.dekaustubh.bingo.rooms.create.CreateRoomContract
import com.dekaustubh.bingo.rooms.create.CreateRoomPresenter
import com.dekaustubh.bingo.rooms.create.RoomRepository
import com.dekaustubh.bingo.rooms.create.RoomRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RoomModule {
    @Binds
    abstract fun provideRoomRepository(roomRepositoryImpl: RoomRepositoryImpl): RoomRepository

    @Binds
    abstract fun provideCreateRoomPresenter(createRoomPresenter: CreateRoomPresenter): CreateRoomContract.Presenter

    @Binds
    abstract fun provideFetchRoomPresenter(fetchRoomPresenter: FetchRoomPresenter): FetchRoomsContract.Presenter
}