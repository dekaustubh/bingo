package com.dekaustubh.bingo.register

import android.content.Intent
import com.dekaustubh.bingo.base.BasePresenter
import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.models.User
import com.google.android.gms.games.PlayersClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser

interface RegisterContract {
    interface View: BaseView<Presenter> {
        fun showUser(user: User)
        fun showProgressBar()
    }

    interface Presenter: BasePresenter<View> {
        fun saveUserPreference(user: FirebaseUser)
    }
}