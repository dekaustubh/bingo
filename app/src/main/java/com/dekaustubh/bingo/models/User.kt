package com.dekaustubh.bingo.models

import com.dekaustubh.bingo.db.entities.DbUser

data class User(
    val id: Long,
    val name: String,
    val email: String = "",
    val token: String = "",
    val rooms: List<Room>
)

fun User.toDbUser(isLoggedIn: Boolean): DbUser {
    return DbUser(id, name, email, token, isLoggedIn)
}
