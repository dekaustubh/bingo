package com.dekaustubh.bingo.db

import androidx.room.ColumnInfo

data class Room(
    val id: Long,
    val name: String,
    @ColumnInfo(name = "leaderboard_id")
    val leaderboardId: Long?,
    @ColumnInfo(name= "created_by")
    val createdBy: Long
)