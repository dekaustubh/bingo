package com.dekaustubh.bingo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dekaustubh.bingo.db.dao.RoomDao
import com.dekaustubh.bingo.db.dao.UserDao
import com.dekaustubh.bingo.db.entities.DbRoom
import com.dekaustubh.bingo.db.entities.DbUser
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@Database(entities = [DbUser::class, DbRoom::class], version = 1)
abstract class BingoDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun roomDao(): RoomDao
}

suspend fun BingoDatabase.loggedInUser(): DbUser {
    return coroutineScope {
        val deferredValue = async { userDao().getLoggedInUser() }
        deferredValue.await()
    }
}
