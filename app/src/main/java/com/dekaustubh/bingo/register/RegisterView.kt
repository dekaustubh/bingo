package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.base.BaseView
import com.dekaustubh.bingo.models.User

interface RegisterView : BaseView<RegisterContract.RegisterPresenter> {
    fun showUser(user: User)
}