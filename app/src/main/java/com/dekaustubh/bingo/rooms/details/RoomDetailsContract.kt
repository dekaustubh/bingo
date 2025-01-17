package com.dekaustubh.bingo.rooms.details

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.models.Match

interface RoomDetailsContract {
    interface View : BaseView<Presenter> {
        fun showMatches(matches: List<Match>)
        fun showMatchCreated(match: Match, userName: String)
    }

    interface Presenter : BasePresenter<View> {
        fun fetchMatchesForRoom(roomId: Long)
    }
}