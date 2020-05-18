package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.models.User
import com.google.firebase.auth.FirebaseUser

interface RegisterContract {
    interface View: BaseView<Presenter> {
        fun showUser(user: User)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter: BasePresenter<View> {
        fun saveUserPreference(user: FirebaseUser)
        fun registerUser(userId: String, userName: String)
    }
}