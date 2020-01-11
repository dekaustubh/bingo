package com.dekaustubh.bingo.websockets

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BingoSocketListener @Inject constructor() : WebSocketListener() {

    private val listeners = mutableListOf<WebSocketListener>()

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Timber.d("Socket closed reason $reason")

        listeners.forEach { it.onClosed(webSocket, code, reason) }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Timber.d("Socket closing reason $reason")

        listeners.forEach { it.onClosing(webSocket, code, reason) }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Timber.e("Socket failure response $response")

        listeners.forEach { it.onFailure(webSocket, t, response) }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Timber.d("Socket message $text")

        listeners.forEach { it.onMessage(webSocket, text) }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Timber.d("Socket message ${bytes.toString()}")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Timber.d("Socket open $response")

        listeners.forEach { it.onOpen(webSocket, response) }
    }

    fun addListener(socketListener: WebSocketListener) {
        listeners.add(socketListener)
    }

    fun removeListener(socketListener: WebSocketListener) {
        listeners.remove(socketListener)
    }
}