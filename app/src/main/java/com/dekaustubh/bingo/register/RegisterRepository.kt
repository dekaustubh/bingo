package com.dekaustubh.bingo.register

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.db.BingoDatabase
import com.dekaustubh.bingo.models.User
import com.dekaustubh.bingo.models.toDbUser
import io.reactivex.Single
import javax.inject.Inject

/**
 * Register/Login user related repository.
 * Fetches data from server & then saves it in local db.
 */
interface RegisterRepository {
    fun registerUser(userId: String, name: String, deviceId: String?): Single<User>
    fun updateUser(user: User, loggedInUser: Boolean): Single<User>
}

class RegisterRepositoryImpl @Inject constructor(
    private val bingoApi: BingoApi,
    private val bingoDatabase: BingoDatabase
) : RegisterRepository {
    override fun registerUser(userId: String, name: String, deviceId: String?): Single<User> {
        return bingoApi.registerUser(LoginRequest(userId, name, deviceId))
            .map { userResult ->
                if (userResult.error != null) throw Exception(userResult.error.error)
                val user = userResult.user ?: throw Exception("Error while registering")

                bingoDatabase.userDao().insert(user.toDbUser(true))
                user
            }
    }

    override fun updateUser(user: User, loggedInUser: Boolean): Single<User> {
        return Single.fromCallable {
            bingoDatabase.userDao().insert(user = user.toDbUser(loggedInUser))
            user
        }
    }
}