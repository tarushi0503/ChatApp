package com.example.chatengine.loginScreen


/* Data class for GET API for get all user data with matching credentials
* it holds data using the variables mentioned below that needs to be displayed in the UI
*/
data class LoginDataClass(
    var username:String,
    var secret: String,
    var is_authenticated: Boolean
)
