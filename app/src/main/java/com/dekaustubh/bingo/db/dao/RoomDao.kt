package com.dekaustubh.bingo.db.dao

import androidx.room.*
import com.dekaustubh.bingo.db.entities.DbRoom

@Dao
interface RoomDao {
    @Query("SELECT * FROM room")
    fun getAll(): List<DbRoom>

    @Query("SELECT * FROM room WHERE id = (:roomId)")
    fun getRoomById(roomId: Long): DbRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(room: DbRoom)

    @Update
    fun update(room: DbRoom)

    @Delete
    fun delete(room: DbRoom)

    @Query("DELETE FROM room")
    fun deleteAll()
}