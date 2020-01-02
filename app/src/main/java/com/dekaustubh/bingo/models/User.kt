package com.dekaustubh.bingo.models

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val token: String,
    val rooms: List<Room>
)
