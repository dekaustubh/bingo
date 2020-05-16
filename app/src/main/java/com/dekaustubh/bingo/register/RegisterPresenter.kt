package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.models.User
import com.dekaustubh.bingo.preferences.LoginPreference
import com.google.android.gms.games.PlayersClient
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val loginPreference: LoginPreference
) : RegisterContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var view: RegisterContract.View? = null

    override fun signIn(playersClient: PlayersClient) {
        playersClient.currentPlayer.addOnCompleteListener { task ->
            val player = task.result
            player?.let {
                Timber.d("Got player with name ${it.displayName}")
                // TODO fetch player
                view?.showUser(
                    User(it.playerId.toLong(), it.displayName, "", "", emptyList())
                )
            } ?: view?.showError("Error while retrieving player")
        }
    }

    override fun attach(view: RegisterContract.View) {
        this.view = view
    }

    override fun detach() {
        compositeDisposable.clear()
        view = null
    }
}