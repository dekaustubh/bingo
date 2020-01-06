package com.dekaustubh.bingo.apis

import com.dekaustubh.bingo.match.MatchResult
import com.dekaustubh.bingo.match.MatchesResult
import com.dekaustubh.bingo.models.results.RoomResult
import com.dekaustubh.bingo.models.results.RoomsResult
import com.dekaustubh.bingo.models.results.UserResult
import com.dekaustubh.bingo.register.LoginRequest
import com.dekaustubh.bingo.rooms.create.CreateRoomRequest
import io.reactivex.Single
import retrofit2.http.*

interface BingoApi {
    @GET("user/{id}")
    fun getPeople(@Path("id") userId: Long): Single<UserResult>

    @POST("user/register")
    fun registerUser(@Body loginRequest: LoginRequest): Single<UserResult>

    @POST("user/login")
    fun loginUser(@Body loginRequest: LoginRequest): Single<UserResult>

    @POST("room/create")
    fun createRoom(@Header("token") token: String, @Body createRoomRequest: CreateRoomRequest): Single<RoomResult>

    @GET("rooms/")
    fun getRooms(@Header("token") token: String): Single<RoomsResult>

    @GET("room/{id}")
    fun getRoomById(@Header("token") token: String, @Path("id") id: Long): Single<RoomResult>

    @GET("room/{id}/matches")
    fun getRoomsMatches(
        @Header("token") token: String, @Path("id") id: Long, @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20
    ): Single<MatchesResult>

    @POST("room/{id}/match/create")
    fun startMatchForRoom(@Header("token") token: String, @Path("id") id: Long): Single<MatchResult>
}