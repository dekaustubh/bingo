package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.preferences.LoginPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val loginPreference: LoginPreference
) : RegisterContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var view: RegisterContract.View? = null

    override fun registerUser(name: String, email: String, password: String) {
        compositeDisposable.add(
            registerRepository.registerUser(name, email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { user ->
                    loginPreference.saveLoggedInUser(user.id)
                    view?.showUser(user)
                },
                { e ->
                    Timber.e(e)
                    view?.showError(e.message ?: "Error while registering user.")
                }
            )
        )
    }

    override fun loginUser(email: String, password: String) {
        compositeDisposable.add(
            registerRepository.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { user ->
                        loginPreference.saveLoggedInUser(user.id)
                        view?.showUser(user)
                    },
                    { e ->
                        Timber.e(e)
                        view?.showError(e.message ?: "Error while registering user.")
                    }
                )
        )
    }

    override fun attach(view: RegisterContract.View) {
        this.view = view
    }

    override fun detach() {
        compositeDisposable.clear()
        view = null
    }
}