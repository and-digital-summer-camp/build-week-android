package com.and.newton.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.and.newton.common.utils.AppPreferences.clear
import okhttp3.internal.cache2.Relay.Companion.edit

object AppPreferences {



    private const val NAME = "USER_STATE"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    //SharedPreferences variables
    private val IS_LOGGED = Pair("is_Logged", false)
    private val FIRST_NAME = Pair("first_name", "")
    private val LAST_NAME = Pair("last_name", "")
    private val EMAIL = Pair("email", "")
    private val ACCESS_LEVEL =  Pair("access_level", "")
    private val TOKEN =  Pair("token", "")
    private val TIME_VALID =  Pair("time_valid", "")
    private val USER_PIC = Pair("user_pic", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    //an inline function to put variable and save it
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun clear() {
      val editor: SharedPreferences.Editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    //SharedPreferences variables getters/setters
    var isLogged: Boolean
        get() = preferences.getBoolean(IS_LOGGED.first, IS_LOGGED.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_LOGGED.first, value)
        }


    var first_name: String
        get() = preferences.getString(FIRST_NAME.first, FIRST_NAME.second) ?: ""
        set(value) = preferences.edit {
            it.putString(FIRST_NAME.first, value)
        }

    var last_name: String
        get() = preferences.getString(LAST_NAME.first, LAST_NAME.second) ?: ""
        set(value) = preferences.edit {
            it.putString(LAST_NAME.first, value)
        }

    var email: String
        get() = preferences.getString(EMAIL.first, EMAIL.second) ?: ""
        set(value) = preferences.edit {
            it.putString(EMAIL.first, value)
        }

    var access_level: String
        get() = preferences.getString(ACCESS_LEVEL.first, ACCESS_LEVEL.second) ?: ""
        set(value) = preferences.edit {
            it.putString(ACCESS_LEVEL.first, value)
        }

    var token: String
        get() = preferences.getString(TOKEN.first, TOKEN.second) ?: ""
        set(value) = preferences.edit {
            it.putString(TOKEN.first, value)
        }

    var time_valid: String
        get() = preferences.getString(TIME_VALID.first, TIME_VALID.second) ?: System.currentTimeMillis().toString()
        set(value) = preferences.edit {
            it.putString(TIME_VALID.first, value)
        }

    var user_pic: String
        get() = preferences.getString(USER_PIC.first, USER_PIC.second) ?: ""
        set(value) = preferences.edit {
            it.putString(USER_PIC.first, value)
        }
}