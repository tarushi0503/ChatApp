package com.example.chatengine.userHistoryScreen

import com.example.chatengine.userHistoryScreen.DataClass.LastMessage
import com.example.chatengine.userHistoryScreen.DataClass.People

data class GetChatsDataClass(
    val created: String,
    val last_message: LastMessage,
    val people: List<People>,
    val title: String,
    val id: Int,
    val access_key: String
)