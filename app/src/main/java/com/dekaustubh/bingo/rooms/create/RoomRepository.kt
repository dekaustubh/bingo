package com.dekaustubh.bingo.rooms.create

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.constants.DI.USER_TOKEN
import com.dekaustubh.bingo.db.BingoDatabase
import com.dekaustubh.bingo.models.Room
import com.dekaustubh.bingo.models.results.RoomResult
import com.dekaustubh.bingo.models.toDbRoom
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

interface RoomRepository {
    fun createRoom(name: String): Single<Room>
}

class RoomRepositoryImpl @Inject constructor(
    private val bingoApi: BingoApi,
    @Named(USER_TOKEN) private val token: String,
    private val bingoDatabase: BingoDatabase
): RoomRepository {
    override fun createRoom(name: String): Single<Room> {
        return bingoApi.createRoom(token, CreateRoomRequest(name))
            .map { roomResult ->
                val room = roomResult.room
                if (roomResult.error != null) throw Exception(roomResult.error.error)
                if (room == null) throw java.lang.Exception("Error while getting room")

                bingoDatabase.roomDao().insert(room.toDbRoom())

                room
            }
    }
}