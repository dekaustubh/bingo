package com.dekaustubh.bingo.main.listeners

import com.dekaustubh.bingo.models.Room

/**
 * Click listener for selecting room.
 */
interface OnRoomSelectListener {
    fun onRoomSelected(room: Room)
}