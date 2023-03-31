package com.example.chatengine.viewModel


import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatengine.questionsRoom.RoomDataClass
import com.example.chatengine.questionsScreen.ChatRoom
import com.example.chatengine.isTyping.TypingClass
import com.example.chatengine.isTyping.isTypingInterface
import com.example.chatengine.loginScreen.LoginClass
import com.example.chatengine.loginScreen.LoginDataClass
import com.example.chatengine.loginScreen.LoginInterfaceAPI
import com.example.chatengine.messageScreen.MessageClass
import com.example.chatengine.messageScreen.MsgDataClassModel
import com.example.chatengine.messageScreen.RecieveDataClass
import com.example.chatengine.messageScreen.messageApiInterface
import com.example.chatengine.questionsScreen.RoomApiInterface
import com.example.chatengine.signupScreen.SignUpClass
import com.example.chatengine.signupScreen.signUpApiInterface
import com.example.chatengine.userHistoryScreen.GetChatHistoryApiInterface
import com.example.chatengine.userHistoryScreen.GetChatsDataClass
import com.example.chatengine.userHistoryScreen.GetMyChatsClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel:ViewModel() {

    //stores the username of user
    var username = mutableStateOf("")

    //stores the password of user
    var password = mutableStateOf("")

    //stores the chatId of room created
    var chatId by mutableStateOf(-1)

    //stores accesskey of the room created
     var accesskey by mutableStateOf("")

    //manages whether or not to show the loader
    var isLoading = mutableStateOf(false)

    //name with which the chat room will be created
    var chatName by mutableStateOf("Agent")


    val initial = LoginDataClass("","",false)
    var UserData: LoginDataClass? by mutableStateOf(initial)


    //signup function inherits signUpApiInterface and passes the values to SignUpClass for passing the desired input to headers
    fun signup(): signUpApiInterface {
        val signUpApiInterface= SignUpClass(username.value,password.value).postInstance()
        return  signUpApiInterface
    }

    //login function to authenticate user
    fun AuthenticateUser(): LoginInterfaceAPI {
        val apiService= LoginClass(username.value,password.value).getInstance()
        return  apiService
    }

    //create new chat room
    //var chat by mutableStateOf("")
    var initialChat= RoomDataClass("",false, listOf("tarushi07"))
    var newChatDetails: RoomDataClass? by mutableStateOf(initialChat)
    fun createChat(): RoomApiInterface {
        val chatApiService = ChatRoom(username.value,password.value).postRoomInstance()
        return  chatApiService
    }

    //post messages
    var text by mutableStateOf("")
    var firstMsg = MsgDataClassModel("")
    var newMsgDetails:MsgDataClassModel? by mutableStateOf(firstMsg)
    fun createMsg(): messageApiInterface {
        val msgApiService= MessageClass(username.value,password.value,chatId).postMsgInstance()
        return  msgApiService
    }




    private val _messageList = MutableStateFlow(emptyList<RecieveDataClass>())
    val messageList: StateFlow<List<RecieveDataClass>> = _messageList

    fun updateMessageList(newList: List<RecieveDataClass>) {
        _messageList.value = newList
    }

    //get messages
    var firstMsgGet : MutableList<RecieveDataClass> by mutableStateOf(mutableListOf())
    fun createMsgGet():messageApiInterface{
        val msgApiService= MessageClass(username.value,password.value,chatId).getMsgInstance()
        return  msgApiService
    }
    //update chatting messages
    fun updateList(msg:RecieveDataClass){
        firstMsgGet.add(msg)
    }



    //Different users
    var allChats : MutableList<GetChatsDataClass> by mutableStateOf(mutableListOf())
    //var newMsgDetailsGet:MsgDataClassModel? by mutableStateOf(firstMsgGet)
    fun getAllChats(): GetChatHistoryApiInterface {
        val msgApiService= GetMyChatsClass(username.value,password.value).getMsgInstance()
        return  msgApiService
    }



    //user is typing or not
    val istyping = mutableStateOf(false)
    val istypinguser= mutableStateOf("")

    @SuppressLint("SuspiciousIndentation")
    fun IsUserTyping(): isTypingInterface {
        val apiService= TypingClass(username.value,password.value,chatId.toString()).getTypingInstance()
        return apiService
    }
    fun startTyping(){
        viewModelScope.launch {
            withContext(Dispatchers.Default){
                delay(2000L)
            }
            istyping.value=false
        }
    }
}


