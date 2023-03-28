package com.example.chatengine.userScreen

import com.example.chatengine.userScreen.DataClass.LastMessage
import com.example.chatengine.userScreen.DataClass.People

data class GetChatsDataClass(
    val created: String,
    val last_message: LastMessage,
    val people: List<People>,
    val title: String
)