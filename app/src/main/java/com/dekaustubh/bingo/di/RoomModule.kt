package com.dekaustubh.bingo.di

import com.dekaustubh.bingo.main.MainContract
import com.dekaustubh.bingo.main.MainPresenter
import com.dekaustubh.bingo.register.FetchRoomPresenter
import com.dekaustubh.bingo.register.FetchRoomsContract
import com.dekaustubh.bingo.rooms.create.CreateRoomContract
import com.dekaustubh.bingo.rooms.create.CreateRoomPresenter
import com.dekaustubh.bingo.rooms.RoomRepository
import com.dekaustubh.bingo.rooms.RoomRepositoryImpl
import com.dekaustubh.bingo.rooms.details.RoomDetailsContract
import com.dekaustubh.bingo.rooms.details.RoomDetailsPresenter
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

    @Binds
    abstract fun provideRoomDetailPresenter(roomDetailsPresenter: RoomDetailsPresenter): RoomDetailsContract.Presenter

    @Binds
    abstract fun provideMainPresenter(mainPresenter: MainPresenter): MainContract.Presenter
}