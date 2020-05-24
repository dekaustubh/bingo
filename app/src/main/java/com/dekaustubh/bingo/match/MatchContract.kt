package com.dekaustubh.bingo.match

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView

interface MatchContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {
        fun joinMatch(roomId: Long, matchId: Long)
    }
}