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
import com.example.chatengine.loginScreen.LoginScreen
import com.example.chatengine.webSocket.WebSocketManager
import com.example.chatengine.viewModel.MainViewModel
import com.example.chatengine.messageScreen.Messages
import com.example.chatengine.questionsRoom.QuestionsList
import com.example.chatengine.questionsRoom.QuestionsViewModel
import com.example.chatengine.signupScreen.SignUpScaffold
import com.example.chatengine.userHistoryScreen.UserScreen


/*
* code defines a composable function called NavigationController that handle different screens
* The function uses the NavHost to define the different screens in the app.
* The screens are defined using the composable function, which takes in a route string and a composable function that defines the screen's UI.
 */
@RequiresApi(Build.VERSION_CODES.O)  //indicate that it requires API level 26 or higher.
@Composable
fun NavigationController(sharedPreferences: SharedPreferences, navController: NavHostController = rememberNavController()) {
   val questionViewModel: QuestionsViewModel = viewModel()

    val mainViewModel: MainViewModel = viewModel()
    val webSocketManager=WebSocketManager(mainViewModel)

    //startDestination defines the first screen that will be visible to the user
    NavHost(navController = navController, startDestination = NavigationItems.LoginScreen.route){

        // A screen where users can log in to the app.
        composable(NavigationItems.LoginScreen.route){
            LoginScreen(navController,mainViewModel,sharedPreferences)
        }

        // A screen where users can sign up for the app.
        composable(NavigationItems.SignUpScaffold.route){
            SignUpScaffold(navController,mainViewModel)
        }

        //A screen that displays all users.
        composable(NavigationItems.UserScreen.route){
            UserScreen(navController, mainViewModel,sharedPreferences)
        }

        // A screen that displays messages between users.
        composable(NavigationItems.Messages.route){
            Messages(navController,mainViewModel,webSocketManager)
        }

        //A screen that displays a list of questions.
        composable(NavigationItems.QuestionsList.route){
            QuestionsList(navController, mainViewModel,questionViewModel)
        }

    }
}