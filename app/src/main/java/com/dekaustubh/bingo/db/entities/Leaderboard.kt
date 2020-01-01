package com.dekaustubh.bingo.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Leaderboard(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "room_id") val roomId: Long,
    val points: Int?
)