package com.dekaustubh.bingo.models

import com.dekaustubh.bingo.db.entities.DbRoom
import com.google.gson.annotations.SerializedName

data class Room(
    val id: Long,
    val name: String,
    @SerializedName("leaderboard_id")
    val leaderboardId: Long?,
    @SerializedName("created_by")
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