package com.example.chatengine.userHistoryScreen

import com.example.chatengine.userHistoryScreen.DataClass.LastMessage
import com.example.chatengine.userHistoryScreen.DataClass.People


/* Data class for GET API for get all chat user history
* it holds data using the variables mentioned below that needs to be displayed in the UI
* It is a nested data class that hold List of type data class
*/
data class GetChatsDataClass(
    val created: String,
    val last_message: LastMessage,
    val people: List<People>,
    val title: String,
    val id: Int,
    val access_key: String
)