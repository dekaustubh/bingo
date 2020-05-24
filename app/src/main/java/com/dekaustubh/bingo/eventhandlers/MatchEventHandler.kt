package com.dekaustubh.bingo.eventhandlers

import com.dekaustubh.bingo.constants.DI
import com.dekaustubh.bingo.websockets.WebsocketEvent
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MatchEventHandler @Inject constructor(
    @Named(DI.USER_ID) private val loggedInUserId: String
): BaseEventHandler() {
    override fun handleEvent(event: WebsocketEvent) {
        if (event.userId == loggedInUserId) {
            // Ignore event updates for self user.
            return
        }
        eventListeners.forEach {
            it.onNewEvent(event)
        }
    }
}