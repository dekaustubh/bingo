package com.dekaustubh.bingo.rooms.create

import com.dekaustubh.bingo.apis.BingoApi
import com.dekaustubh.bingo.constants.DI.USER_TOKEN
import com.dekaustubh.bingo.models.results.RoomResult
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

interface RoomRepository {
    fun createRoom(name: String): Single<RoomResult>
}

class RoomRepositoryImpl @Inject constructor(
    private val bingoApi: BingoApi,
    @Named(USER_TOKEN) private val token: String
): RoomRepository {
    override fun createRoom(name: String): Single<RoomResult> {
        return bingoApi.createRoom(token, CreateRoomRequest(name))
    }
}