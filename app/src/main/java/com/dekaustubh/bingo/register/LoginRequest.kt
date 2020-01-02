package com.dekaustubh.bingo.register

data class LoginRequest(
    val name: String?,
    val email: String,
    val password: String
)