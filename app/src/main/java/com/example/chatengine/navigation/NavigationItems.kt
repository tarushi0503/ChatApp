package com.example.chatengine.navigation

sealed class NavigationItems(val route:String) {


    object getDataLogin : NavigationItems("getDataLogin")
    object PostDataSignUpScaffold : NavigationItems("PostDataSignUpScaffold")
    object UserScreen : NavigationItems("UserScreen")
    object  Chat: NavigationItems("chat")

    object Messages: NavigationItems("messages")
    object QuestionsList:NavigationItems("question_list")

}