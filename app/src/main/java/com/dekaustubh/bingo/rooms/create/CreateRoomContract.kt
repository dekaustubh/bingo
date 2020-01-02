package com.dekaustubh.bingo.rooms.create

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.models.Room

interface CreateRoomContract {
    interface View: BaseView<Presenter> {
        fun showRoom(room: Room)
    }

    interface Presenter: BasePresenter<View> {
        fun createRoom(name: String)
    }
}