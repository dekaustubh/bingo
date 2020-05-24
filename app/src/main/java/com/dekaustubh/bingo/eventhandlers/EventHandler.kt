package com.dekaustubh.bingo.eventhandlers

import com.dekaustubh.bingo.websockets.WebsocketEvent

interface EventHandler {
    fun handleEvent(event: WebsocketEvent)
    fun addEventListener(eventListener: EventListener)
    fun removeEventListener(eventListener: EventListener)
}

interface EventListener {
    fun onNewEvent(websocketEvent: WebsocketEvent)
}