package com.dekaustubh.bingo.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dekaustubh.bingo.db.entities.DbRoom

@Dao
interface RoomDao {
    @Query("SELECT * FROM room")
    fun getAll(): List<DbRoom>

    @Query("SELECT * FROM room WHERE id = (:roomId)")
    fun getRoomById(roomId: Long): DbRoom

    @Insert
    fun insert(room: DbRoom)

    @Delete
    fun delete(room: DbRoom)

    @Query("DELETE FROM room")
    fun deleteAll()
}