package com.example.chatengine.messageScreen

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatengine.loginScreen.LoginViewModel
import com.example.chatengine.ui.theme.*
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
            loginViewModel.newMsgDetails = model

        }

        override fun onFailure(call: Call<MsgDataClassModel?>, t: Throwable) {
            result.value="error "+t.message
        }
    })

}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Messages(navController: NavHostController, loginViewModel: LoginViewModel) {

    val message = remember {
        mutableStateOf("")
    }

    val result = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current


    Scaffold(
       topBar = {
            TopAppBar(backgroundColor = Purple200,
                title = {
                    Text(
                        text = if(loginViewModel.user_name=="tarushi07") "yash07" else "tarushi07",
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
                        //textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) { // Use NavHostController to handle navigation back
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
            )}
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(690.dp)
                .background(Color.White)
                .padding(8.dp)
        ) {
            itemsIndexed(loginViewModel.firstMsgGet) { index, item ->

                val isCurrentUser = item.sender_username == loginViewModel.user_name
                val messageBackgroundColor = if (isCurrentUser) SenderColor else ReceiverColor
                val messageTextColor = if (isCurrentUser) Color.Black else Color.Black

                val time = item.created.subSequence(11, 16)

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = if (isCurrentUser) Alignment.End else Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {

                        Card(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp, 0.dp, 9.dp, 12.dp)),
                            elevation = 10.dp,

                            ) {
                            Column(
                                modifier = Modifier
                                    .background(messageBackgroundColor)
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = item.text,
                                    color = messageTextColor,
                                    style = TextStyle(fontSize = 16.sp),
                                )
                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "$time",
                                    color = messageTextColor,
                                    style = TextStyle(fontSize = 12.sp),
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }
                }
            }
        }



        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = message.value, onValueChange = {
                        message.value = it
                    },
                    placeholder = { Text(text = "Start typing..", color = Color.LightGray)}
                )
                IconButton(onClick = {
                    startMessaging(context, message.value, result, loginViewModel)
                    message.value = ""
                },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(Icons.Filled.Send, contentDescription = "", tint = Purple200)

                }
            }
            Text(
                text = result.value,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }

}





