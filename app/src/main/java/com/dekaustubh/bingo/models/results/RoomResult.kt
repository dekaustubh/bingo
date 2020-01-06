package com.dekaustubh.bingo.models.results

import com.dekaustubh.bingo.models.Room

data class RoomResult(
    val error: Error? = null,
    val success: Success? = null,
    val room: Room?
)

data class RoomsResult(
    val error: Error? = null,
    val success: Success? = null,
    val rooms: List<Room>
)