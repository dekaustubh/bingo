package com.dekaustubh.bingo.rooms.details

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.match.Match

interface RoomDetailsContract {
    interface View : BaseView<Presenter> {
        fun showMatches(matches: List<Match>)
    }

    interface Presenter : BasePresenter<View> {
        fun fetchMatchesForRoom(roomId: Long)
    }
}