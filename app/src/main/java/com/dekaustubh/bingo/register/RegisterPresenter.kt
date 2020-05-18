package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.preferences.UserPreference
import com.dekaustubh.bingo.utils.plusAssign
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val loginPreference: UserPreference,
    private val registerRepository: RegisterRepository
) : RegisterContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var view: RegisterContract.View? = null

    override fun attach(view: RegisterContract.View) {
        this.view = view
    }

    override fun registerUser(userId: String, userName: String) {
        view?.showProgressBar()
        compositeDisposable += registerRepository.registerUser(userId, userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.hideProgressBar()
                    view?.showUser(it)
                },
                {
                    Timber.e(it, "Error while registering user.")
                    view?.showError("Error while registering user.")
                    view?.hideProgressBar()
                }
            )
    }

    override fun detach() {
        compositeDisposable.clear()
        view = null
    }

    override fun saveUserPreference(user: FirebaseUser) {
        loginPreference.saveLoggedInUser(user.uid)
    }
}