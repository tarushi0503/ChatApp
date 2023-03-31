package com.example.chatengine.navigation

sealed class NavigationItems(val route:String) {
    object LoginScreen : NavigationItems("getDataLogin")
    object SignUpScaffold : NavigationItems("PostDataSignUpScaffold")
    object UserScreen : NavigationItems("UserScreen")

    object Messages: NavigationItems("messages")
    object QuestionsList:NavigationItems("question_list")

}