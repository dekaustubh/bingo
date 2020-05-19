package com.dekaustubh.bingo.constants

import com.dekaustubh.bingo.BuildConfig

object ApiConstants {

    const val BASE_URL = "b3c70078.ngrok.io"

    fun getBaseUrl(): String {
        return if (BuildConfig.DEBUG) {
            "https://$BASE_URL/api/v1/"
        } else {
            ""
        }
    }
}

object DI {
    const val USER_TOKEN = "user_token"
}