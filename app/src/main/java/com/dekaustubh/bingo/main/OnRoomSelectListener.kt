package com.dekaustubh.bingo.main

import com.dekaustubh.bingo.models.Room
import javax.inject.Inject

/**
 * Click listener for selecting room.
 */
interface OnRoomSelectListener {
    fun onRoomSelected(room: Room)
}