package com.dekaustubh.bingo.register

/**
 * Class for sending json data to register/login API.
 */
data class LoginRequest(
    val id: String,
    val name: String?
)