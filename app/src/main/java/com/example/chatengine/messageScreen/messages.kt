package com.example.chatengine.messageScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.chatengine.ChatScreen.ChatDataClass
import com.example.chatengine.Navigation.NavigationItems
import com.example.chatengine.loginScreen.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun startMessaging(context: Context, value: String, result: MutableState<String>,  loginViewModel: LoginViewModel) {

    val retrofitAPI = loginViewModel.createMsg()
    val msgDataClassModel=MsgDataClassModel(value)

    val call: Call<MsgDataClassModel?>? = retrofitAPI.postMsg(msgDataClassModel)

    call!!.enqueue(object : Callback<MsgDataClassModel?> {

        override fun onResponse(call: Call<MsgDataClassModel?>, response: Response<MsgDataClassModel?>) {
            Toast.makeText(context, " in", Toast.LENGTH_SHORT).show()
            val model: MsgDataClassModel? = response.body()
            val resp = model?.text
            if (resp != null) {
                result.value=resp
            }
//            secret.value = model?.secret.toString()
            loginViewModel.newMsgDetails = model

        }

        override fun onFailure(call: Call<MsgDataClassModel?>, t: Throwable) {
            result.value="error "+t.message
        }
    })

}



@Composable
fun Messages(navController: NavHostController, loginViewModel: LoginViewModel) {

    val message= remember {
        mutableStateOf("")
    }

    val result= remember {
        mutableStateOf("")
    }
    val context= LocalContext.current

    LazyColumn(modifier = Modifier.fillMaxSize()
    ){
        itemsIndexed(loginViewModel.firstMsgGet){  index: Int, item ->  
            Text(text = item.text)
//            Text(text = item.created)
//            Text(text = item.sender_username)
        }
    }

    Box(
        modifier= Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        Row(
            modifier= Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = message.value, onValueChange = {
                    message.value=it
                })
            IconButton(onClick = {
                startMessaging(context,message.value,result,loginViewModel)
            }) {
                Icon(Icons.Filled.Send, contentDescription = "")

            }
        }
        
        Text(text = result.value,
            modifier = Modifier.align(Alignment.TopEnd)
        )

    }
}




