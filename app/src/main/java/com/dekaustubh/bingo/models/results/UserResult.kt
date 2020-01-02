package com.dekaustubh.bingo.models.results

import com.dekaustubh.bingo.models.User

data class UserResult(
    val error: Error? = null,
    val success: Success? = null,
    val user: User?
)
