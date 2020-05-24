package com.dekaustubh.bingo.eventhandlers

import com.dekaustubh.bingo.websockets.WebsocketEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEventHandler @Inject constructor(): BaseEventHandler() {
    override fun handleEvent(event: WebsocketEvent) {
        eventListeners.forEach {
            it.onNewEvent(event)
        }
    }
}