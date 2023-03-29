package com.example.chatengine.Navigation

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatengine.ChatScreen.Chat
import com.example.chatengine.WebSocket.WebSocketManager
import com.example.chatengine.loginScreen.LoginViewModel
import com.example.chatengine.loginScreen.getDataLogin
import com.example.chatengine.messageScreen.Messages
import com.example.chatengine.signupScreen.PostDataSignUpScaffold
import com.example.chatengine.userScreen.UserScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationController(sharedPreferences: SharedPreferences, navController: NavHostController = rememberNavController()) {

    val loginViewModel:LoginViewModel= viewModel()
    val webSocketManager=WebSocketManager(loginViewModel)


    NavHost(navController = navController, startDestination = NavigationItems.getDataLogin.route){
        composable(NavigationItems.getDataLogin.route){
            getDataLogin(navController,loginViewModel,sharedPreferences)
        }
//        composable(NavigationItem.Navigation.route){
//            Navigation()
//        }

        composable(NavigationItems.PostDataSignUpScaffold.route){
            PostDataSignUpScaffold(navController,loginViewModel)
        }

        composable(NavigationItems.UserScreen.route){
            UserScreen(navController, loginViewModel,sharedPreferences)
        }

        composable(NavigationItems.Chat.route){
            Chat(navController)
        }

        composable(NavigationItems.Messages.route){
            Messages(navController,loginViewModel,webSocketManager)
        }

    }}