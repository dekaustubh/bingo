package com.dekaustubh.bingo.common

import com.dekaustubh.bingo.db.BingoDatabase
import com.dekaustubh.bingo.db.entities.DbUser
import com.dekaustubh.bingo.models.User
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

interface UserRepository {
    fun getLoggedInUser(): Single<User?>
    fun getUserById(id: Long): Single<User?>
}

class UserRepositoryImpl @Inject constructor(
    private val bingoDatabase: BingoDatabase
) : UserRepository {

    override fun getLoggedInUser(): Single<User?> {
        var dbUser : DbUser? = null
        CoroutineScope(Dispatchers.IO).launch {
            dbUser = bingoDatabase.userDao().getLoggedInUser()
        }
        return if (dbUser == null) {
            Single.error(Throwable("User not logged in"))
        } else {
            Single.just(User(dbUser!!.id, dbUser!!.name, dbUser!!.token ?: ""))
        }
    }

    override fun getUserById(id: Long): Single<User?> {
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