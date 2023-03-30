package com.example.chatengine.navigation

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatengine.webSocket.WebSocketManager
import com.example.chatengine.viewModel.MainViewModel
import com.example.chatengine.loginScreen.getDataLogin
import com.example.chatengine.messageScreen.Messages
import com.example.chatengine.questionsRoom.QuestionsList
import com.example.chatengine.questionsRoom.QuestionsViewModel
import com.example.chatengine.signupScreen.PostDataSignUpScaffold
import com.example.chatengine.userScreen.UserScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationController(sharedPreferences: SharedPreferences, navController: NavHostController = rememberNavController()) {
   val questionViewModel: QuestionsViewModel = viewModel()

    val mainViewModel: MainViewModel = viewModel()
    val webSocketManager=WebSocketManager(mainViewModel)


    NavHost(navController = navController, startDestination = NavigationItems.getDataLogin.route){
        composable(NavigationItems.getDataLogin.route){
            getDataLogin(navController,mainViewModel,sharedPreferences)
        }

        composable(NavigationItems.PostDataSignUpScaffold.route){
            PostDataSignUpScaffold(navController,mainViewModel)
        }

        composable(NavigationItems.UserScreen.route){
            UserScreen(navController, mainViewModel,sharedPreferences)
        }

//        composable(NavigationItems.Chat.route){
//            Chat(navController,mainViewModel)
//        }

        composable(NavigationItems.Messages.route){
            Messages(navController,mainViewModel,webSocketManager)
        }

        composable(NavigationItems.QuestionsList.route){
            QuestionsList(navController, mainViewModel,questionViewModel)
        }

    }}