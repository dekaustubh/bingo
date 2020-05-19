package com.dekaustubh.bingo.helpers

import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JsonHelper @Inject constructor() {

    private val gson = Gson()

    fun toString(obj: Any): String {
        return gson.toJson(obj)
    }

    fun <T> toObj(jsonString: String, kClass: Class<T>): T {
        return gson.fromJson(jsonString, kClass)
    }
}