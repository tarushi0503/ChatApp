package com.example.chatengine.loginScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chatengine.ChatScreen.ChatApiInterface
import com.example.chatengine.ChatScreen.ChatDataClass
import com.example.chatengine.ChatScreen.ChatRoom

class LoginViewModel:ViewModel() {

    var user_name by mutableStateOf("")
    var password by mutableStateOf("")

    val initial = LoginDataClass("","",false)
    var UserData: LoginDataClass? by mutableStateOf(initial)

    fun AuthenticateUser():LoginInterfaceAPI{
        val apiService=LoginClass(user_name,password).getInstance()

        return  apiService
    }


    //create new chat room
    //var chat by mutableStateOf("")
    var initialChat=ChatDataClass("",false)
    var newChatDetails:ChatDataClass? by mutableStateOf(initialChat)
    fun createChat():ChatApiInterface{
        val chatApiService = ChatRoom(user_name,password).postRoomInstance()
        return  chatApiService
    }
}


//
//class LoginViewModel: ViewModel() {
//    var user_name by mutableStateOf("")
//    var password by mutableStateOf("")
//
//    val initial = LoginDataClass("","",false)
//    var UserData: LoginDataClass? by mutableStateOf(initial)
//
//    val initail2 = ChatDataClass("",false)
//    var chatData: ChatDataClass? by mutableStateOf(initail2)
//    fun AuthenticateUser():LoginInterfaceAPI{
//        val apiService=LoginClass(user_name,password).getInstance()
//
//        return  apiService
//    }
//    fun createRoom():ChatApiInterface{
//        val chatRoomApi = ChatRoomClass(user_name,password).postInstance()
//        return chatRoomApi
//    }
//}