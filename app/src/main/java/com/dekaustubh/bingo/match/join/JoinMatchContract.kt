package com.dekaustubh.bingo.match.join

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView

interface JoinMatchContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View> {
        fun connectToWebSocket()
        fun joinMatch(roomId: Long, matchId: Long)
    }
}