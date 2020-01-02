package com.dekaustubh.bingo.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class DbUser(
    @PrimaryKey val id: Long,
    val name: String,
    val email: String,
    val token: String?
)
