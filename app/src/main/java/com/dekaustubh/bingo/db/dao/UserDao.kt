package com.dekaustubh.bingo.db.dao

import androidx.room.*
import com.dekaustubh.bingo.db.entities.DbUser

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<DbUser>

    @Query("SELECT * FROM user WHERE id = (:userId)")
    fun getUserById(userId: String): DbUser?

    @Query("SELECT * FROM user WHERE loggedInUser = (:loggedIn)")
    fun getLoggedInUser(loggedIn: Boolean = true): DbUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: DbUser)

    @Delete
    fun delete(user: DbUser)

    @Query("DELETE FROM user")
    fun deleteAll()
}