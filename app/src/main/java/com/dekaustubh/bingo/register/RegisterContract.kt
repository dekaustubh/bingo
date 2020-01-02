package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.models.User

interface RegisterContract {
    interface View: BaseView<Presenter> {
        fun showUser(user: User)
    }

    interface Presenter: BasePresenter<View> {
        fun registerUser(name: String, email: String, password: String)
    }
}