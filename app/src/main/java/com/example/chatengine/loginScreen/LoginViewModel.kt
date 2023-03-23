package com.example.chatengine.loginScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chatengine.ChatScreen.ChatApiInterface
import com.example.chatengine.ChatScreen.ChatDataClass
import com.example.chatengine.ChatScreen.ChatRoom
import com.example.chatengine.messageScreen.MessageClass
import com.example.chatengine.messageScreen.MsgDataClassModel
import com.example.chatengine.messageScreen.PostMessageAPI
import com.example.chatengine.messageScreen.RecieveDataClass

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

    //post messages
    var text by mutableStateOf("")
    var firstMsg = MsgDataClassModel("")
    var newMsgDetails:MsgDataClassModel? by mutableStateOf(firstMsg)
    fun createMsg():PostMessageAPI{
        val msgApiService= MessageClass(user_name,password).postMsgInstance()
        return  msgApiService
    }

    //get messages
    //var msgGet by mutableStateOf("")
    var firstMsgGet : List<RecieveDataClass> by mutableStateOf(listOf())
    //var newMsgDetailsGet:MsgDataClassModel? by mutableStateOf(firstMsgGet)
    fun createMsgGet():PostMessageAPI{
        val msgApiService= MessageClass(user_name,password).getMsgInstance()
        return  msgApiService
    }
}

