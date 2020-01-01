package com.dekaustubh.bingo.preferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


const val LOGIN_PREF = "login"

@Singleton
class LoginPreference @Inject constructor(private val context: Context) {


    private val loginPref: SharedPreferences =
        context.applicationContext.getSharedPreferences(LOGIN_PREF, 0)


    /**
     * Checks if user is logged in.
     * @return [true] if user is logged in.
     */
    fun isUsedLoggedIn(): Boolean {
        return loginPref.getLong("logged_in_user_id", 0) != 0L
    }

    /**
     * Saves [userId] of logged in user.
     */
    fun saveLoggedInUser(userId: Long) {
        loginPref.edit()
            .putLong("logged_in_user_id", userId)
            .apply()
    }
}