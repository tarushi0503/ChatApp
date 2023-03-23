package com.example.chatengine.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatengine.ChatScreen.Chat
import com.example.chatengine.loginScreen.LoginViewModel
import com.example.chatengine.loginScreen.getDataLogin
import com.example.chatengine.messageScreen.Messages
import com.example.chatengine.signupScreen.PostDataSignUpScaffold
import com.example.chatengine.userScreen.UserScreen
import kotlin.math.log


@Composable
fun NavigationController(navController: NavHostController = rememberNavController()) {

    val loginViewModel:LoginViewModel= viewModel()

    NavHost(navController = navController, startDestination = NavigationItems.getDataLogin.route){
        composable(NavigationItems.getDataLogin.route){
            getDataLogin(navController,loginViewModel)
        }
//        composable(NavigationItem.Navigation.route){
//            Navigation()
//        }

        composable(NavigationItems.PostDataSignUpScaffold.route){
            PostDataSignUpScaffold(navController)
        }

        composable(NavigationItems.UserScreen.route){
            UserScreen(navController, loginViewModel)
        }

        composable(NavigationItems.Chat.route){
            Chat(navController)
        }

        composable(NavigationItems.Messages.route){
            Messages()
        }

    }}