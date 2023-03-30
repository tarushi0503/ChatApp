package com.example.chatengine.messageScreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatengine.circularProgressIndicator.LoadingView
import com.example.chatengine.webSocket.WebSocketManager
import com.example.chatengine.isTyping.isTypingDataClass
import com.example.chatengine.viewModel.MainViewModel
import com.example.chatengine.ui.theme.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun IsTypingHelpingFunction(
    context: Context,
    viewModel: MainViewModel
)
{
    val retrofitAPI= viewModel.IsUserTyping()
    val call: Call<isTypingDataClass?>? = retrofitAPI.notifyTyping()
    call!!.enqueue(object : Callback<isTypingDataClass?> {
        override fun onResponse(call: Call<isTypingDataClass?>?, response: Response<isTypingDataClass?>) {
            val model: isTypingDataClass? = response.body()
            val resp =
                "Response Code : " + response.code()
        }
        override fun onFailure(call: Call<isTypingDataClass?>?, t: Throwable) {
            var temp = "Error found is : " + t.message
            Toast.makeText(context,temp, Toast.LENGTH_SHORT).show()
        }
    })
}


fun startMessaging(context: Context, value: String, result: MutableState<String>, mainViewModel: MainViewModel) {

    val retrofitAPI = mainViewModel.createMsg()
    val msgDataClassModel=MsgDataClassModel(value)

    val call: Call<MsgDataClassModel?>? = retrofitAPI.postMsg(msgDataClassModel)

    call!!.enqueue(object : Callback<MsgDataClassModel?> {

        override fun onResponse(call: Call<MsgDataClassModel?>, response: Response<MsgDataClassModel?>) {
            //Toast.makeText(context, " in", Toast.LENGTH_SHORT).show()
            val model: MsgDataClassModel? = response.body()
            val resp = model?.text
            if (resp != null) {
                result.value=resp
            }
            mainViewModel.newMsgDetails = model

        }

        override fun onFailure(call: Call<MsgDataClassModel?>, t: Throwable) {
            result.value="error "+t.message
        }
    })
}



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Messages(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    webSocketManager: WebSocketManager
) {

    val messageListState = mainViewModel.messageList.collectAsState()
    val messageList = messageListState.value
    Text(text = messageList.size.toString())

    var inputText by remember {
        mutableStateOf("")
    }

    val result = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Purple200,
                title = {
                    if (mainViewModel.istyping.value && mainViewModel.user_name.value != mainViewModel.istypinguser.value) {
                        Text(text = " is typing")
                        mainViewModel.startTyping()
//                    isTYping=false
                    } else {
                        Text(
                            text = if (mainViewModel.user_name.value == "tarushi07") "" else "tarushi07",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp),
                            //textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) { // Use NavHostController to handle navigation back
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
            )
        },

        ) {

        if (mainViewModel.firstMsgGet.size == 0) {
            Box(
                modifier = Modifier.height(500.dp)
                    .width(800.dp),
                contentAlignment = Alignment.Center
            ){
                Text(text = "No Chat History",
                fontSize = 20.sp)
            }

        } else {


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(690.dp)
                    .background(Color.White)
                    .padding(8.dp),

                reverseLayout = true

            ) {
                itemsIndexed(mainViewModel.firstMsgGet.sortedByDescending { it.created }) { lastIndex, item ->

                    val isCurrentUser = item.sender_username == mainViewModel.user_name.value
                    val messageBackgroundColor = if (isCurrentUser) SenderColor else ReceiverColor
                    val messageTextColor = if (isCurrentUser) Color.Black else Color.Black
                    val time = item.created.subSequence(11, 16)
                    val date = item.created.subSequence(8, 10)
                    var month = item.created.subSequence(5, 7)
                    val year = item.created.subSequence(0, 4)
                    month = when (month) {
                        "01" -> "Jan"
                        "02" -> "Feb"
                        "03" -> "March"
                        "04" -> "April"
                        "05" -> "May"
                        "06" -> "June"
                        "07" -> "July"
                        "08" -> "Aug"
                        "09" -> "Sept"
                        "10" -> "Oct"
                        "11" -> "Nov"
                        else -> "Dec"
                    }

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
                                        modifier = Modifier.align(Alignment.End)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = "$date $month $year",
                                        color = messageTextColor,
                                        style = TextStyle(fontSize = 12.sp),
                                        modifier = Modifier.align(Alignment.End)
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
                    value = inputText,
                    maxLines = 2,
                    onValueChange = {
                        inputText = it
                        IsTypingHelpingFunction(context, mainViewModel)

                    },
                    placeholder = { Text(text = "Start typing..", color = Color.LightGray) }
                )
                IconButton(
                    onClick = {
                        webSocketManager.sendMessage(inputText)
                        startMessaging(context, inputText, result, mainViewModel)
                        inputText = ""
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(Icons.Filled.Send, contentDescription = "", tint = Purple200)

                }
            }
        }

    }

    if (mainViewModel.isLoading.value == true) {
        LoadingView()
    }

}




