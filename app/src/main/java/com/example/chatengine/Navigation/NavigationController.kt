package com.example.chatengine.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatengine.loginScreen.getDataLogin
import com.example.chatengine.signupScreen.PostDataSignUpScaffold
import com.example.chatengine.userScreen.UserScreen


@Composable
fun NavigationController(navController: NavHostController = rememberNavController()) {


    NavHost(navController = navController, startDestination = NavigationItems.getDataLogin.route){

        composable(NavigationItems.getDataLogin.route){
            getDataLogin(navController)
        }
//        composable(NavigationItem.Navigation.route){
//            Navigation()
//        }

        composable(NavigationItems.PostDataSignUpScaffold.route){
            PostDataSignUpScaffold(navController)
        }

        composable(NavigationItems.UserScreen.route){
            UserScreen(navController)
        }

    }}