package com.dekaustubh.bingo.websockets

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okio.ByteString
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WebSocketConnector @Inject constructor(
    private val client: OkHttpClient,
    private val socketListener: BingoSocketListener
) {

    private lateinit var socket: WebSocket
    private var isConnected = false

    fun connect() {
        val request = Request.Builder().url("ws://eca67d56.ngrok.io/connect").build()

        if (isConnected) {
            Timber.w("Socket is already connected")
            return
        }

        socket = client.newWebSocket(
            request,
            socketListener
        )
        isConnected = true
    }

    fun send(bytes: ByteString) = socket.send(bytes)

    fun send(text: String) = socket.send(text)

    fun close(code: WebSocketCloseCode): Boolean {
        val result = socket.close(code.value, null)
        if (result) {
            isConnected = false
        }
        return result
    }
}

/**
 * Codes needed for closing websockets.
 * {@link https://tools.ietf.org/html/rfc6455#section-7.4}
 */
enum class WebSocketCloseCode(val value: Int) {
    NORMAL(1000),
    GOING_AWAY(1001),
    ENDPOINT_TERMINATE(1002),
    ENDPOINT_TERMINATE_INVALID_DATA(1003)
}