package com.dekaustubh.bingo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dekaustubh.bingo.db.dao.RoomDao
import com.dekaustubh.bingo.db.dao.UserDao
import com.dekaustubh.bingo.db.entities.Room
import com.dekaustubh.bingo.db.entities.User

@Database(entities = [User::class, Room::class], version = 1)
abstract class BingoDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun roomDao(): RoomDao
}
