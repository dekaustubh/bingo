package com.dekaustubh.bingo.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Long,
    val name: String,
    val email: String,
    val token: String?
)
