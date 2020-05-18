package com.dekaustubh.bingo.register

import com.google.gson.annotations.SerializedName

/**
 * Class for sending json data to register/login API.
 */
data class LoginRequest(
    val id: String,
    val name: String?,
    @SerializedName("device_id")
    val deviceId: String?
)