package com.dekaustubh.bingo.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Leaderboard(
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "room_id") val roomId: Long,
    val points: Int?
)