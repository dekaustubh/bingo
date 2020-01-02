package com.dekaustubh.bingo.apis

import com.dekaustubh.bingo.models.User
import com.dekaustubh.bingo.models.results.UserResult
import com.dekaustubh.bingo.register.LoginRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BingoApi {
    @GET("user/{id}")
    fun getPeople(@Path("id") userId: Long): Single<UserResult>

    @POST("user/register")
    fun registerUser(@Body loginRequest: LoginRequest): Single<UserResult>
}