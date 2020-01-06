package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.models.Room
import com.dekaustubh.bingo.models.User

interface FetchRoomsContract {
    interface View: BaseView<Presenter> {
        fun showRooms(rooms: List<Room>)
        fun showRoom(room: Room)
    }

    interface Presenter: BasePresenter<View> {
        fun fetchRooms()
        fun fetchRoomById(roomId: Long)
    }
}