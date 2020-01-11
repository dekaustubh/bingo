package com.dekaustubh.bingo.constants

import com.dekaustubh.bingo.BuildConfig

object ApiConstants {

    fun getBaseUrl(): String {
        return if (BuildConfig.DEBUG) {
            "https://eca67d56.ngrok.io/api/v1/"
        } else {
            ""
        }
    }
}

object DI {
    const val USER_TOKEN = "user_token"
}