package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.preferences.LoginPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class RegisterPresenterImpl @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val loginPreference: LoginPreference
) : RegisterContract.RegisterPresenter {

    private val compositeDisposable = CompositeDisposable()
    private var view: RegisterContract.RegisterView? = null

    override fun registerUser(name: String, email: String, password: String) {
        compositeDisposable.add(
            registerRepository.registerUser(name, email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val user = it.user
                    if (it.error != null) throw Exception(it.error.error)
                    if (user == null) throw Exception("Error while registering")

                    loginPreference.saveLoggedInUser(user.id)
                    view?.showUser(user)
                },
                {
                    Timber.e(it)
                    view?.showError(it.message ?: "Error while registering user.")
                }
            )
        )
    }

    override fun attach(view: RegisterContract.RegisterView) {
        this.view = view
    }

    override fun detach() {
        compositeDisposable.clear()
        view = null
    }
}