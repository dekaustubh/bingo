package com.dekaustubh.bingo.db

import androidx.room.Entity

@Entity
data class User(
    val id: Long,
    val name: String,
    val email: String,
    val token: String?
)
