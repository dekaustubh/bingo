package com.dekaustubh.bingo.rooms

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.constants.DI.USER_TOKEN
import com.dekaustubh.bingo.db.BingoDatabase
import com.dekaustubh.bingo.models.Room
import com.dekaustubh.bingo.models.toDbRoom
import com.dekaustubh.bingo.rooms.create.CreateRoomRequest
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

interface RoomRepository {
    fun createRoom(name: String): Single<Room>
    fun getRoomById(roomId: Long): Single<Room>
    fun getRooms(): Single<List<Room>>
}

class RoomRepositoryImpl @Inject constructor(
    private val bingoApi: BingoApi,
    @Named(USER_TOKEN) private val token: String,
    private val bingoDatabase: BingoDatabase
): RoomRepository {
    override fun createRoom(name: String): Single<Room> {
        return bingoApi.createRoom(token,
            CreateRoomRequest(name)
        )
            .map { roomResult ->
                val room = roomResult.room
                if (roomResult.error != null) throw Exception(roomResult.error.error)
                if (room == null) throw java.lang.Exception("Error while getting room")

                bingoDatabase.roomDao().insert(room.toDbRoom())

                room
            }
    }

    override fun getRoomById(roomId: Long): Single<Room> {
        return bingoApi.getRoomById(token, roomId)
            .map {  roomResult ->
                val room = roomResult.room
                if (roomResult.error != null) throw Exception(roomResult.error.error)
                if (room == null) throw java.lang.Exception("Error while getting room")

                bingoDatabase.roomDao().update(room.toDbRoom())

                room
            }
    }

    override fun getRooms(): Single<List<Room>> {
        return bingoApi.getRooms(token)
            .map { roomsResult ->
                if (roomsResult.error != null) throw Exception(roomsResult.error.error)

                val rooms = roomsResult.rooms
                val list = mutableListOf<Room>()
                rooms.forEach {
                    bingoDatabase.roomDao().insert(it.toDbRoom())
                    list.add(it)
                }

                list
            }
    }
}