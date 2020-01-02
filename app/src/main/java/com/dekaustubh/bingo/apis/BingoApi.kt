package com.dekaustubh.bingo.apis

import com.dekaustubh.bingo.models.results.RoomResult
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
}