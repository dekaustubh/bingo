package com.dekaustubh.bingo.splash

import com.dekaustubh.bingo.common.UserRepository
import com.dekaustubh.bingo.utils.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val userRepository: UserRepository
): SplashContract.Presenter {

    private val disposable = CompositeDisposable()
    private var view: SplashContract.View? = null

    override fun getLoggedInUser() {
        disposable += userRepository.getLoggedInUser()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it?.let {
                        view?.showHomeScreen()
                    } ?: view?.showRegisterScreen()
                },
                {
                    Timber.e(it, "Error while getting loggedin user")
                    view?.showRegisterScreen()
                }
            )
    }

    override fun attach(view: SplashContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
        disposable.clear()
    }

}