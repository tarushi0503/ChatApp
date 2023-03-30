package com.example.chatengine.ChatScreen

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatengine.Navigation.NavigationItems
import com.example.chatengine.loginScreen.LoginViewModel
import com.example.chatengine.ui.theme.Purple500
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Chat(navController: NavController, loginViewModel: LoginViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Admin")},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Use NavHostController to handle navigation back
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }

            )
        }
    ) {

        val context = LocalContext.current
        val title = loginViewModel.user_name

        val result = remember {
            mutableStateOf("")
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.BottomCenter
        ) {

            Button(
                onClick = {
                    postRoom(context, title.value, result, navController, loginViewModel)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                //.background(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Purple500,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(70.dp),
            ) {
                Text(text = "Chat with Admin", fontWeight = FontWeight.Bold)
            }
        }
    }
}


private fun postRoom(
    ctx: Context,
    title: String,
    result: MutableState<String>,
    navController: NavController,
    loginViewModel: LoginViewModel
) {

    val retrofitAPI = loginViewModel.createChat()
    val chatDataClass=ChatDataClass(title,false, listOf("tarushi07"))

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
//            loginViewModel.chatId= loginViewModel.newChatDetails!!.id
//            loginViewModel.accessId= loginViewModel.newChatDetails!!.access_key

            //print("################################################### ${loginViewModel.chatId}")
            if(model?.is_direct_chat==false){
                Toast.makeText(ctx,"Chat created", Toast.LENGTH_LONG).show()
                navController.navigate(NavigationItems.UserScreen.route)
            }

        }

        override fun onFailure(call: Call<ChatDataClass?>, t: Throwable) {
            result.value="error "+t.message
        }
    })
}