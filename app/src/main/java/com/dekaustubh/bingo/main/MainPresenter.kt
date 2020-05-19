package com.dekaustubh.bingo.main

import com.dekaustubh.bingo.common.UserRepository
import com.dekaustubh.bingo.helpers.JsonHelper
import com.dekaustubh.bingo.utils.plusAssign
import com.dekaustubh.bingo.websockets.UserConnected
import com.dekaustubh.bingo.websockets.WebSocketCloseCode
import com.dekaustubh.bingo.websockets.WebSocketHelper
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val webSocketHelper: WebSocketHelper,
    private val userRepository: UserRepository,
    private val jsonHelper: JsonHelper
): MainContract.Presenter {

    private val disposable = CompositeDisposable()
    private var view: MainContract.View? = null

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun detach() {
        disposable.clear()
        view = null
    }

    override fun connectToWebSocket() {
        disposable += webSocketHelper.connect()
            .subscribe(
                {
                    Timber.d("Connected to websocket")
                    sendUserConnectedEvent()
                },
                {
                    Timber.e(it, "error while connecting to websocket")
                    view?.showError("Error while communicating with server")
                }
            )
    }

    override fun disconnectToWebSocket(code: WebSocketCloseCode) {
        disposable += webSocketHelper.disconnect(code)
            .subscribe(
                {
                    Timber.d("Disconnected from websocket")
                },
                {
                    Timber.e(it, "error while dis-connecting from websocket")
                }
            )
    }

    private fun sendUserConnectedEvent() {
        disposable += userRepository.getLoggedInUser()
            .map { user ->
                webSocketHelper.send(
                    jsonHelper.toString(
                        UserConnected(user.id)
                    )
                )
            }
            .subscribe(
                {
                    Timber.d("Message sent successfully")
                },
                {
                    Timber.e(it, "error while sending connect message")
                }
            )
    }
}