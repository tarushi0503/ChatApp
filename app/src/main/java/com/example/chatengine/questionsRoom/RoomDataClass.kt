package com.example.chatengine.questionsRoom


/* Data class for Post API for creating chat room
* it holds data using the variables mentioned below that needs to be displayed in the UI
*/

data class RoomDataClass (
    var title: String,
    var is_direct_chat : Boolean,
    var usernames: List<String>
)

