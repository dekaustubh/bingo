package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.models.User

interface RegisterContract {
    interface RegisterView: BaseView<RegisterPresenter> {
        fun showUser(user: User)
    }

    interface RegisterPresenter: BasePresenter<RegisterView> {
        fun registerUser(name: String, email: String, password: String)
    }
}