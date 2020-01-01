package com.dekaustubh.bingo.apis

import com.dekaustubh.bingo.db.entities.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BingoApi {
    @GET("/user/{id}")
    fun getPeople(@Path("id") userId: Long): Call<User>
}