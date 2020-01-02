package com.dekaustubh.bingo.models

import com.dekaustubh.bingo.db.entities.DbRoom

data class Room(
    val id: Long,
    val name: String,
    val leaderboardId: Long?,
    val createdBy: Long
)

fun Room.toDbRoom(): DbRoom {
    return DbRoom(
        id = id,
        name = name,
        leaderboardId = leaderboardId,
        createdBy = createdBy
    )
}