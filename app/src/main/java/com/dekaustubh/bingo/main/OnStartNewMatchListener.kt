package com.dekaustubh.bingo.main

import com.dekaustubh.bingo.models.Room


interface OnStartNewMatchListener {
    fun onNewMatchStarted(room: Room)
}