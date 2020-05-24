package com.dekaustubh.bingo.eventhandlers


abstract class BaseEventHandler: EventHandler {

    protected val eventListeners = mutableSetOf<EventListener>()

    override fun addEventListener(eventListener: EventListener) {
        eventListeners.add(eventListener)
    }

    override fun removeEventListener(eventListener: EventListener) {
        eventListeners.remove(eventListener)
    }
}