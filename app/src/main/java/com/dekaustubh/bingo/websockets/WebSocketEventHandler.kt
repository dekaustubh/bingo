package com.dekaustubh.bingo.websockets

import com.dekaustubh.bingo.eventhandlers.MatchEventHandler
import com.google.gson.Gson
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketEventHandler @Inject constructor(
    private val gson: Gson,
    private val matchEventHandler: MatchEventHandler
): WebSocketListener() {

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        handleEvent(bytes.toString())
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        handleEvent(text)
    }

    private fun handleEvent(text: String) {
        val webSocketEvent = gson.fromJson(text, WebsocketEvent::class.java)
        when {
            (webSocketEvent is MatchCreated) or (webSocketEvent is MatchStarted) -> {
                matchEventHandler.handleEvent(webSocketEvent)
            }
            else -> Timber.w("Can't handle event $webSocketEvent")
        }
    }
}