package com.dekaustubh.bingo.splash

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView

interface SplashContract {
    interface View: BaseView<Presenter> {
        fun showRegisterScreen()
        fun showHomeScreen()
    }

    interface Presenter: BasePresenter<View> {
        fun getLoggedInUser()
    }
}