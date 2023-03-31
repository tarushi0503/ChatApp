package com.example.chatengine.userHistoryScreen.DataClass


//it holds data using the variables mentioned below that needs to be displayed in the UI
data class LastMessage(
    val attachments: List<Any>,
    val created: String,
    val custom_json: Any,
    val sender_username: String,
    val text: String
)