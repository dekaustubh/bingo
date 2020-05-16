package com.dekaustubh.bingo.preferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


const val USER_PREF = "user_pref"

// User related key constants
private const val LOGGED_IN_USER_ID_KEY = "logged_in_user_id"

@Singleton
class UserPreference @Inject constructor(private val context: Context) {


    private val loginPref: SharedPreferences =
        context.applicationContext.getSharedPreferences(USER_PREF, 0)


    /**
     * Checks if user is logged in.
     * @return [true] if user is logged in.
     */
    fun isUsedLoggedIn(): Boolean {
        return !loginPref.getString(LOGGED_IN_USER_ID_KEY, "").isNullOrEmpty()
    }

    /**
     * Saves [userId] of logged in user.
     */
    fun saveLoggedInUser(userId: String) {
        loginPref.edit()
            .putString(LOGGED_IN_USER_ID_KEY, userId)
            .apply()
    }
}