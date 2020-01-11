package com.dekaustubh.bingo.websockets

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import okio.ByteString
import javax.inject.Inject

class WebSocketHelper @Inject constructor(private val webSocketConnector: WebSocketConnector) {

    fun connect(): Completable {
        return Completable.fromCallable { webSocketConnector.connect() }
            .subscribeOn(Schedulers.io())
    }

    fun disconnect(code: WebSocketCloseCode): Completable {
        return Completable.fromCallable { webSocketConnector.close(code) }
            .subscribeOn(Schedulers.io())
    }

    fun send(byteString: ByteString) = webSocketConnector.send(byteString)

    fun send(text: String) = webSocketConnector.send(text)
}