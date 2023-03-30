package com.example.chatengine.viewModel


import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatengine.questionsScreen.ChatApiInterface
import com.example.chatengine.questionsScreen.ChatDataClass
import com.example.chatengine.questionsScreen.ChatRoom
import com.example.chatengine.isTyping.TypingClass
import com.example.chatengine.isTyping.isTypingInterface
import com.example.chatengine.loginScreen.LoginClass
import com.example.chatengine.loginScreen.LoginDataClass
import com.example.chatengine.loginScreen.LoginInterfaceAPI
import com.example.chatengine.messageScreen.MessageClass
import com.example.chatengine.messageScreen.MsgDataClassModel
import com.example.chatengine.messageScreen.PostMessageAPI
import com.example.chatengine.messageScreen.RecieveDataClass
import com.example.chatengine.signupScreen.RetrofitAPI
import com.example.chatengine.signupScreen.SignUpClass
import com.example.chatengine.userScreen.GetChatsDataClass
import com.example.chatengine.userScreen.GetMyChats
import com.example.chatengine.userScreen.GetMyChatsClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel:ViewModel() {

    var user_name = mutableStateOf("")
    var password = mutableStateOf("")
    var chatId by mutableStateOf(-1)
     var accesskey by mutableStateOf("")

    var isLoading = mutableStateOf(false)

    val initial = LoginDataClass("","",false)
    var UserData: LoginDataClass? by mutableStateOf(initial)

    var chatName by mutableStateOf("Agent")

    //signup
    fun signup():RetrofitAPI{
        val msgApiService= SignUpClass(user_name.value,password.value).postInstance()
        return  msgApiService
    }

    fun AuthenticateUser(): LoginInterfaceAPI {
        val apiService= LoginClass(user_name.value,password.value).getInstance()
        return  apiService
    }

    //create new chat room
    //var chat by mutableStateOf("")
    var initialChat=ChatDataClass("",false, listOf("tarushi07"))
    var newChatDetails:ChatDataClass? by mutableStateOf(initialChat)
    fun createChat():ChatApiInterface{
        val chatApiService = ChatRoom(user_name.value,password.value).postRoomInstance()
        return  chatApiService
    }

    //post messages
    var text by mutableStateOf("")
    var firstMsg = MsgDataClassModel("")
    var newMsgDetails:MsgDataClassModel? by mutableStateOf(firstMsg)
    fun createMsg():PostMessageAPI{
        val msgApiService= MessageClass(user_name.value,password.value,chatId).postMsgInstance()
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
        val msgApiService= MessageClass(user_name.value,password.value,chatId).getMsgInstance()
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
        val msgApiService= GetMyChatsClass(user_name.value,password.value).getMsgInstance()
        return  msgApiService
    }



    //user is typing or not
    val istyping = mutableStateOf(false)
    val istypinguser= mutableStateOf("")

    @SuppressLint("SuspiciousIndentation")
    fun IsUserTyping(): isTypingInterface {
        val apiService= TypingClass(user_name.value,password.value,chatId.toString()).getTypingInstance()
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


