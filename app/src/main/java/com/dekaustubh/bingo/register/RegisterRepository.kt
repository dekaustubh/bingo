package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.models.User
import com.dekaustubh.bingo.models.results.UserResult
import io.reactivex.Single
import javax.inject.Inject

interface RegisterRepository {
    fun registerUser(name: String, email: String, password: String): Single<UserResult>
}

class RegisterRepositoryImpl @Inject constructor(
    private val bingoApi: BingoApi
) : RegisterRepository {
    override fun registerUser(name: String, email: String, password: String): Single<UserResult> {
        return bingoApi.registerUser(LoginRequest(name, email, password))
    }
}