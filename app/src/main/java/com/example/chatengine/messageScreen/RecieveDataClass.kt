package com.example.chatengine.messageScreen


/* Data class for GET API for get all message history
* it holds data using the variables mentioned below that needs to be displayed in the UI
*/
data class RecieveDataClass (
    val text: String,
    val created:String,
    var sender_username: String
    )