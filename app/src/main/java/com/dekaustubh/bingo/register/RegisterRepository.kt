package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.db.BingoDatabase
import com.dekaustubh.bingo.models.User
import com.dekaustubh.bingo.models.toDbUser
import io.reactivex.Single
import javax.inject.Inject

interface RegisterRepository {
    fun registerUser(name: String, email: String, password: String): Single<User>
    fun loginUser(email: String, password: String): Single<User>
}

class RegisterRepositoryImpl @Inject constructor(
    private val bingoApi: BingoApi,
    private val bingoDatabase: BingoDatabase
) : RegisterRepository {
    override fun registerUser(name: String, email: String, password: String): Single<User> {
        return bingoApi.registerUser(LoginRequest(name, email, password))
            .map { userResult ->
                if (userResult.error != null) throw Exception(userResult.error.error)
                val user = userResult.user ?: throw Exception("Error while registering")

                bingoDatabase.userDao().insert(user.toDbUser(true))
                user
            }
    }

    override fun loginUser(email: String, password: String): Single<User> {
        return bingoApi.loginUser(LoginRequest(null, email, password))
            .map { userResult ->
                if (userResult.error != null) throw Exception(userResult.error.error)
                val user = userResult.user ?: throw Exception("Error while login")

                bingoDatabase.userDao().insert(user.toDbUser(true))
                user
            }
    }
}