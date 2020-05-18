package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.preferences.UserPreference
import com.dekaustubh.bingo.utils.plusAssign
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.NullPointerException
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

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                val deviceId = if (!task.isSuccessful) {
                    Timber.e(task.exception, "Failed to get user device id")
                    null
                } else {
                    // Get new device ID token
                    task.result?.token
                }

                compositeDisposable += registerRepository.registerUser(userId, userName, deviceId)
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
            })
    }

    override fun detach() {
        compositeDisposable.clear()
        view = null
    }

    override fun saveUserPreference(user: FirebaseUser) {
        loginPreference.saveLoggedInUser(user.uid)
    }
}