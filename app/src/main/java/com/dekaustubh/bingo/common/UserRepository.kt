package com.dekaustubh.bingo.common

import com.dekaustubh.bingo.db.BingoDatabase
import com.dekaustubh.bingo.db.entities.DbUser
import com.dekaustubh.bingo.models.User
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

interface UserRepository {
    fun getLoggedInUser(): Single<User?>
    fun getUserById(id: String): Single<User?>
}

class UserRepositoryImpl @Inject constructor(
    private val bingoDatabase: BingoDatabase
) : UserRepository {

    override fun getLoggedInUser(): Single<User?> {
        return Single.fromCallable {
            val dbUser = bingoDatabase.userDao().getLoggedInUser()
            User(dbUser?.id ?: "", dbUser?.name ?: "", dbUser?.token ?: "")
        }.subscribeOn(Schedulers.computation())
    }

    override fun getUserById(id: String): Single<User?> {
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