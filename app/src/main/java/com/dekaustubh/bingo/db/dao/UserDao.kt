package com.dekaustubh.bingo.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dekaustubh.bingo.db.entities.DbUser

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<DbUser>

    @Query("SELECT * FROM user WHERE id = (:userId)")
    fun getUserById(userId: Long): DbUser

    @Query("SELECT * FROM user WHERE loggedInUser = (:loggedIn)")
    fun getLoggedInUser(loggedIn: Boolean = true): DbUser

    @Insert
    fun insert(user: DbUser)

    @Delete
    fun delete(user: DbUser)

    @Query("DELETE FROM user")
    fun deleteAll()
}