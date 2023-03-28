package com.example.chatengine.Logout

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtil(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean("isLoggedIn", false)
        set(value) = sharedPreferences.edit().putBoolean("isLoggedIn", value).apply()
}