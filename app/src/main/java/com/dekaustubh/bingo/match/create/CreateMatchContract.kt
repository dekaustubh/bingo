package com.dekaustubh.bingo.match.create

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.models.Match
import com.dekaustubh.bingo.models.Room

interface CreateMatchContract {
    interface View : BaseView<Presenter> {
        fun startOrUpdateMatch(match: Match)
    }

    interface Presenter : BasePresenter<View> {
        fun startMatchForRoom(room: Room)
    }
}