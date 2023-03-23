package com.example.chatengine.loginScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.net.Authenticator

class LoginViewModel:ViewModel() {

    var user_name by mutableStateOf("")
    var password by mutableStateOf("")

    val initial = LoginDataClass("","",false)
    var UserData: LoginDataClass? by mutableStateOf(initial)

    fun AuthenticateUser():LoginInterfaceAPI{
        val apiService=LoginClass(user_name,password).getInstance()

        return  apiService
    }
}