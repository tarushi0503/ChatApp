package com.example.chatengine


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.chatengine.Navigation.NavigationController

class MainActivity : ComponentActivity() {
    //private val chatViewModel: ChatViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //ChatScreen(chatViewModel)
            NavigationController()
        }
    }
}




