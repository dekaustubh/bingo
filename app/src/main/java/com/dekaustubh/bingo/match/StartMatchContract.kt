package com.dekaustubh.bingo.match

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.models.Room

interface StartMatchContract {
    interface View : BaseView<Presenter> {
        fun startOrUpdateMatch(match: Match)
    }

    interface Presenter : BasePresenter<View> {
        fun startMatchForRoom(room: Room)
    }
}