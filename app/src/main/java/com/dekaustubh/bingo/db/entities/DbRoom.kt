package com.dekaustubh.bingo.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room")
data class DbRoom(
    @PrimaryKey val id: Long,
    val name: String,
    @ColumnInfo(name = "leaderboard_id")
    val leaderboardId: String?,
    @ColumnInfo(name= "created_by")
    val createdBy: String
)