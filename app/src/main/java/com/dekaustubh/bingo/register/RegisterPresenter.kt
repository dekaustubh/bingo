package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.preferences.UserPreference
import com.google.firebase.auth.FirebaseUser
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val loginPreference: UserPreference
) : RegisterContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var view: RegisterContract.View? = null

    override fun attach(view: RegisterContract.View) {
        this.view = view

    }

    override fun detach() {
        compositeDisposable.clear()
        view = null
    }

    override fun saveUserPreference(user: FirebaseUser) {
        loginPreference.saveLoggedInUser(user.uid)
    }
}