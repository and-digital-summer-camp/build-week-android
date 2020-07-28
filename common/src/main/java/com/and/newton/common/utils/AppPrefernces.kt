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
    private val USERNAME = Pair("username", "")
    private val ACCESS_LEVEL =  Pair("access_level", "")


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


    var username: String
        get() = preferences.getString(USERNAME.first, USERNAME.second) ?: ""
        set(value) = preferences.edit {
            it.putString(USERNAME.first, value)
        }

    var access_level: String
        get() = preferences.getString(ACCESS_LEVEL.first, ACCESS_LEVEL.second) ?: ""
        set(value) = preferences.edit {
            it.putString(ACCESS_LEVEL.first, value)
        }
}