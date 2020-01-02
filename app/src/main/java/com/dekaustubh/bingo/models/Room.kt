package com.dekaustubh.bingo.models

data class Room(
    val id: Long,
    val name: String,
    val leaderboardId: Long?,
    val createdBy: Long
)