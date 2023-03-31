package com.example.chatengine.signupScreen

/*
* Data class for POST API for Creating User
* its holds data using the variables mentioned below that needs to be displayed in the UI
* */
data class SignUpDataClass(
    var username:String,
    var first_name: String,
    var last_name: String,
    var secret: String,
)