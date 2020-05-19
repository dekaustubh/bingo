package com.dekaustubh.bingo.models

import com.dekaustubh.bingo.db.entities.DbUser

data class User(
    val id: String,
    val name: String,
    val token: String = "",
    val rooms: List<Room> = emptyList()
)

fun User.toDbUser(isLoggedIn: Boolean): DbUser {
    return DbUser(id, name, token, isLoggedIn)
}

object NO_USER {
    fun get(): User = User("", "", "")
}