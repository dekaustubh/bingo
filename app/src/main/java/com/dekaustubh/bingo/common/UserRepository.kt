package com.dekaustubh.bingo.common

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.db.BingoDatabase
import com.dekaustubh.bingo.models.User
import io.reactivex.Single
import javax.inject.Inject

interface UserRepository {
    fun getUserLoggedInUser(): Single<User>
    fun getUserById(id: Long): Single<User>
}

class UserRepositoryImpl @Inject constructor(
    private val bingoApi: BingoApi,
    private val bingoDatabase: BingoDatabase
) : UserRepository {

    override fun getUserLoggedInUser(): Single<User> {
        return Single.fromCallable { bingoDatabase.userDao().getLoggedInUser() }
            .map { dbUser ->
                User(
                    dbUser.id,
                    dbUser.name,
                    dbUser.token ?: ""
                )
            }
    }

    override fun getUserById(id: Long): Single<User> {
        return Single.fromCallable { bingoDatabase.userDao().getUserById(id) }
            .map { dbUser ->
                User(
                    dbUser.id,
                    dbUser.name,
                    dbUser.token ?: ""
                )
            }
    }
}