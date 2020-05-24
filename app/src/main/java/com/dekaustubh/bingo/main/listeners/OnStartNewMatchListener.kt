package com.dekaustubh.bingo.main.listeners

import com.dekaustubh.bingo.models.Room


interface OnStartNewMatchListener {
    fun onNewMatchStarted(room: Room)
}