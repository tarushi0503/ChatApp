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
import com.example.chatengine.userScreen.GetChatsDataClass
import com.example.chatengine.userScreen.GetMyChats
import com.example.chatengine.userScreen.GetMyChatsClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel:ViewModel() {


    var user_name by mutableStateOf("")
    var password by mutableStateOf("")
//    var chatId by mutableStateOf<DataClassResponse>(null)
     //var chatId by mutableStateOf<DataClassResponse?>(null)

    val initial = LoginDataClass("","",false)
    var UserData: LoginDataClass? by mutableStateOf(initial)

    //logout

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




    private val _messageList = MutableStateFlow(emptyList<RecieveDataClass>())
    val messageList: StateFlow<List<RecieveDataClass>> = _messageList

    fun updateMessageList(newList: List<RecieveDataClass>) {
        _messageList.value = newList
    }

    //get messages
    var firstMsgGet : MutableList<RecieveDataClass> by mutableStateOf(mutableListOf())
    fun createMsgGet():PostMessageAPI{
        val msgApiService= MessageClass(user_name,password).getMsgInstance()
        return  msgApiService
    }
    //update chatting messages
    fun updateList(msg:RecieveDataClass){
        firstMsgGet.add(msg)
    }



    //Different users
    var allChats : MutableList<GetChatsDataClass> by mutableStateOf(mutableListOf())
    //var newMsgDetailsGet:MsgDataClassModel? by mutableStateOf(firstMsgGet)
    fun getAllChats(): GetMyChats {
        val msgApiService= GetMyChatsClass(user_name,password).getMsgInstance()
        return  msgApiService
    }


}