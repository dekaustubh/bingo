package com.dekaustubh.bingo.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Room(
    @PrimaryKey val id: Long,
    val name: String,
    @ColumnInfo(name = "leaderboard_id")
    val leaderboardId: Long?,
    @ColumnInfo(name= "created_by")
    val createdBy: Long
)