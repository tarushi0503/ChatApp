package com.example.chatengine.navigation


/* sealed represents the different screens or destinations.
 The class has a single constructor parameter route which is a string representing the route or URL for the destination.*/
sealed class NavigationItems(val route:String) {
    object LoginScreen : NavigationItems("getDataLogin")
    object SignUpScaffold : NavigationItems("PostDataSignUpScaffold")
    object UserScreen : NavigationItems("UserScreen")

    object Messages: NavigationItems("messages")
    object QuestionsList:NavigationItems("question_list")

}