package com.example.chatengine.webSocket


import android.util.Log
import com.example.chatengine.constants.constants.projectId
import com.example.chatengine.viewModel.MainViewModel
import com.example.chatengine.messageScreen.RecieveDataClass
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.net.SocketException


//WebSocketListener provides methods for handling WebSocket events such as onOpen, onMessage, and onFailure.
class WebSocketManager(private val mainViewModel: MainViewModel):WebSocketListener(){
    private var webSocket: WebSocket


    /*
    * WebSocket instance is created by calling newWebSocket on an instance of the OkHttpClient class.
    * Pass in a request object that specifies the URL of the WebSocket endpoint.
     */
    init {
        val request = Request.Builder().url("wss://api.chatengine.io/chat/?projectID=${projectId}&chatID=${mainViewModel.chatId}&accessKey=${mainViewModel.accesskey}").build()
        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, this)
    }

    //sends a message to the server.
    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("MYTAG", "WebSocket connection established.")
        webSocket.send("Hello, server!")
    }

    /*
    * parses the received message and updates the MainViewModel instance with the received data
    * It checks for two possible types of messages - new messages and typing status updates.*/
    override fun onMessage(webSocket: WebSocket, text: String) {
        val gson = Gson()
        val json = JSONObject(text)
        val action = json.getString("action")

        if (action == "new_message") {
            val message = json.getJSONObject("data").getJSONObject("message")
            val receivedMessage = RecieveDataClass(
                text = message.getString("text"),
                created = message.getString("created"),
                sender_username = message.getString("sender_username")
            )
            mainViewModel.updateList(receivedMessage)
//            loginViewModel.firstMsgGet.add(receivedMessage)
            mainViewModel.updateMessageList((mainViewModel.messageList.value + message) as List<RecieveDataClass>)
            Log.d("MYTAG", "onMessage: $receivedMessage ")
        }

        if (action =="is_typing") {
            val data=json.getJSONObject("data")
            val name=data.getString("person")
            mainViewModel.istyping.value=true
            mainViewModel.istypinguser.value= name.toString()
        }
    }

    fun onClose(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("MYTAG", "WebSocket connection closed.")
    }

    //logs an error message when the WebSocket connection fails
    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        if (t is SocketException && t.message?.contains("Broken pipe") == true) {
            Log.d("MYTAG", "WebSocket failure: Broken pipe")
            // Reconnect the WebSocket here
            val request = Request.Builder()
                .url("wss://api.chatengine.io/chat/?projectID=${projectId}=${mainViewModel.chatId}&accessKey=${mainViewModel.accesskey}")
                .build()
            val client = OkHttpClient()
            this.webSocket = client.newWebSocket(request, this)
        }
        else{Log.d("MYTAG", "WebSocket failure.", t)}
    }

    //allows sending messages
    fun sendMessage(message: String) {
        webSocket.send(message)
    }

    //closes the WebSocket connection
    fun closeWebSocket() {
        webSocket.close(1000, "Closing WebSocket.")
        Log.d("MYTAG","Connection Closed")
    }
}


