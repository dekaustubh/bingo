package com.dekaustubh.bingo.match

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.models.Match
import com.dekaustubh.bingo.models.MatchState

interface MatchContract {
    interface View: BaseView<Presenter> {
        fun showInMatchView(match: Match, state: MatchState)
        fun showUserJoined(name: String)
        fun showUserLeft(name: String)
        fun showStartMatchView()
        fun hideStartMatchView()
    }

    interface Presenter: BasePresenter<View> {
        fun initialize(roomId: Long, matchId: Long)
        fun startMatch()
    }
}