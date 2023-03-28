package com.example.chatengine.WebSocket


import android.util.Log
import com.example.chatengine.loginScreen.LoginViewModel
import com.example.chatengine.messageScreen.RecieveDataClass
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.net.SocketException


class WebSocketManager(private val loginViewModel: LoginViewModel):WebSocketListener(){
    private var webSocket: WebSocket

    init {
        val request = Request.Builder().url("wss://api.chatengine.io/chat/?projectID=0ddfa87c-cd5d-4103-8946-0b0ccc96cf9e&chatID=153494&accessKey=${loginViewModel.accesskey}").build()
        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("MYTAG", "WebSocket connection established.")
        webSocket.send("Hello, server!")
    }

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
            loginViewModel.updateList(receivedMessage)
//            loginViewModel.firstMsgGet.add(receivedMessage)
            loginViewModel.updateMessageList((loginViewModel.messageList.value + message) as List<RecieveDataClass>)
            Log.d("MYTAG", "onMessage: $receivedMessage ")
        }
    }

    fun onClose(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("MYTAG", "WebSocket connection closed.")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        if (t is SocketException && t.message?.contains("Broken pipe") == true) {
            Log.d("MYTAG", "WebSocket failure: Broken pipe")
            // Reconnect the WebSocket here
            val request = Request.Builder()
                .url("wss://api.chatengine.io/chat/?projectID=0ddfa87c-cd5d-4103-8946-0b0ccc96cf9e&chatID=153494&accessKey=ca-468a06ad-8aad-4024-b282-7f1ff509d003")
                .build()
            val client = OkHttpClient()
            this.webSocket = client.newWebSocket(request, this)
        }
        else{Log.d("MYTAG", "WebSocket failure.", t)}
    }

    fun sendMessage(message: String) {
        webSocket.send(message)
    }

    fun closeWebSocket() {
        webSocket.close(1000, "Closing WebSocket.")
        Log.d("MYTAG","Connection Closed")
    }
}


