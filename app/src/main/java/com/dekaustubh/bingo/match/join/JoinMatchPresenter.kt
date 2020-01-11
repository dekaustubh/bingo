package com.dekaustubh.bingo.match.join

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.common.UserRepository
import com.dekaustubh.bingo.constants.DI
import com.dekaustubh.bingo.db.BingoDatabase
import com.dekaustubh.bingo.db.loggedInUser
import com.dekaustubh.bingo.register.RegisterRepository
import com.dekaustubh.bingo.websockets.BingoSocketListener
import com.dekaustubh.bingo.websockets.UserConnected
import com.dekaustubh.bingo.websockets.WebSocketCloseCode
import com.dekaustubh.bingo.websockets.WebSocketHelper
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class JoinMatchPresenter @Inject constructor(
    private val webSocketHelper: WebSocketHelper,
    private val userRepository: UserRepository,
    private val gson: Gson,
    private val bingoSocketListener: BingoSocketListener,
    private val bingoApi: BingoApi,
    @Named(DI.USER_TOKEN) private val token: String
) : JoinMatchContract.Presenter, WebSocketListener() {

    private val disposable = CompositeDisposable()
    private var view : JoinMatchContract.View? = null

    override fun joinMatch(roomId: Long, matchId: Long) {
        disposable.add(
            bingoApi.dummy(token, roomId, matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { matchResult ->
                        val match = matchResult?.match
                        connectToWebSocket()
                    },
                    { e ->
                        Timber.e(e, "Error while joining match")
                        e.message?.let { view?.showError(it) }
                    }
                )
        )
    }

    override fun connectToWebSocket() {
        Timber.d("Started connection to websocket...")
        disposable.add(
            webSocketHelper.connect()
                .subscribe()
        )
    }

    override fun attach(view: JoinMatchContract.View) {
        this.view = view
        connectToWebSocket()
        bingoSocketListener.addListener(this)
    }

    override fun detach() {
        Timber.d("disconnecting to websocket...")
        webSocketHelper.disconnect(WebSocketCloseCode.NORMAL)
        bingoSocketListener.removeListener(this)
        disposable.clear()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)

        Timber.d("Connected to socket")
        disposable.add(
            userRepository.getUserLoggedInUser()
                .map { user ->
                    Timber.d("Got user ${user.id}")
                    webSocketHelper.send(
                        gson.toJson(UserConnected(user.id))
                    )
                }
                .subscribe()
        )
    }
}