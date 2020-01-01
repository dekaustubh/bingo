package com.dekaustubh.bingo.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dekaustubh.bingo.db.entities.Room

@Dao
interface RoomDao {
    @Query("SELECT * FROM room")
    fun getAll(): List<Room>

    @Query("SELECT * FROM room WHERE id = (:roomId)")
    fun getRoomById(roomId: Long): Room

    @Insert
    fun insert(room: Room)

    @Delete
    fun delete(room: Room)

    @Query("DELETE FROM room")
    fun deleteAll()
}