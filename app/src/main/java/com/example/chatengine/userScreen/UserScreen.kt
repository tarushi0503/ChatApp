package com.example.chatengine.userScreen

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.CaseMap.Title
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.chatengine.ChatScreen.ChatDataClass
import com.example.chatengine.Navigation.NavigationItems
import com.example.chatengine.loginScreen.LoginDataClass
import com.example.chatengine.loginScreen.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private fun postRoom(
    ctx: Context,
    title: String,
    result: MutableState<String>,
    navController: NavController,
    loginViewModel: LoginViewModel
) {

    val retrofitAPI = loginViewModel.createChat()
    val chatDataClass=ChatDataClass(title,false)

    val call: Call<ChatDataClass?>? = retrofitAPI.postChatRoom(chatDataClass)


    call!!.enqueue(object : Callback<ChatDataClass?> {

        override fun onResponse(call: Call<ChatDataClass?>, response: Response<ChatDataClass?>) {
            Toast.makeText(ctx, " in", Toast.LENGTH_SHORT).show()
            val model: ChatDataClass? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n"+"Id: " + model?.is_direct_chat+  "\n"+ model?.title
            result.value=resp
//            secret.value = model?.secret.toString()
            loginViewModel.newChatDetails = model
            if(model?.is_direct_chat==false){
                navController.navigate(NavigationItems.Chat.route)
                Toast.makeText(ctx,"Chat created", Toast.LENGTH_LONG).show()
            }

        }

        override fun onFailure(call: Call<ChatDataClass?>, t: Throwable) {
        result.value="error "+t.message
        }
    })
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    val context= LocalContext.current
    val title=loginViewModel.user_name
    val result = remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar= {
            TopAppBar {
                Text(text = "User")
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    postRoom(context,title,result,navController,loginViewModel)
                },
                backgroundColor = Color.Blue,
                contentColor = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .width(50.dp)
                    .height(48.dp)
            ) {
                Icon( Icons.Default.Add, contentDescription = "")
            }
        },

    ) {

        Button(
            onClick = { navController.navigate(NavigationItems.Messages.route) }
        ) {
            Text(text = "Start messaging")
        }
    }
}