package com.dekaustubh.bingo.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class DbUser(
    @PrimaryKey val id: String,
    val name: String,
    val token: String?,
    val loggedInUser: Boolean = false
)
