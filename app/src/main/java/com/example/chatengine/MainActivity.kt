package com.example.chatengine


import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.chatengine.navigation.NavigationController

class MainActivity : ComponentActivity() {
    lateinit var sharedPreferences:SharedPreferences
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
            val username = remember {
                mutableStateOf("")
            }
            val pwd = remember {
                mutableStateOf("")
            }
            username.value = sharedPreferences.getString("USERNAME", "").toString()
            pwd.value = sharedPreferences.getString("SECRET", "").toString()
            NavigationController(sharedPreferences)
        }
    }
}




