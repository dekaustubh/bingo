package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.models.User
import com.google.android.gms.games.PlayersClient

interface RegisterContract {
    interface View: BaseView<Presenter> {
        fun showUser(user: User)
    }

    interface Presenter: BasePresenter<View> {
        fun signIn(playersClient: PlayersClient)
    }
}