package com.example.chatengine.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItems(val route:String) {


    object getDataLogin : NavigationItems("getDataLogin")
    object PostDataSignUpScaffold : NavigationItems("PostDataSignUpScaffold")
    object UserScreen : NavigationItems("UserScreen")

}