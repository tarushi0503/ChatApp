package com.example.chatengine.loginScreen

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val data: LoginDataClass) : LoginState()
    data class Error(val message: String?) : LoginState()
}