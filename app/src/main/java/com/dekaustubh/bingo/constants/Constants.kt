package com.dekaustubh.bingo.constants

import com.dekaustubh.bingo.BuildConfig

object ApiConstants {

    fun getBaseUrl(): String {
        return if (BuildConfig.DEBUG) {
            "https://cb1a6624.ngrok.io/api/v1/"
        } else {
            ""
        }
    }
}