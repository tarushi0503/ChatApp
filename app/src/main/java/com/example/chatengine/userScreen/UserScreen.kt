package com.example.chatengine.userScreen


import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.chatengine.ChatScreen.ChatDataClass
import com.example.chatengine.CircularProgressIndicator.LoadingView
import com.example.chatengine.Navigation.NavigationItems
import com.example.chatengine.loginScreen.LoginViewModel
import com.example.chatengine.messageScreen.RecieveDataClass
import com.example.chatengine.ui.theme.Purple200
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//FLOATING ACTION BUTTON: CREATE NEW ROOM
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
                navController.navigate(NavigationItems.Chat.route)
                Toast.makeText(ctx,"Chat created", Toast.LENGTH_LONG).show()
            }

        }

        override fun onFailure(call: Call<ChatDataClass?>, t: Throwable) {
        result.value="error "+t.message
        }
    })
}


//onCLick -> to chatting screen
private fun getMsgHistory(
    context: Context,
    getApiResult: MutableState<String>,
    loginViewModel: LoginViewModel,
    navController: NavController
) {
    val retrofitAPI = loginViewModel.createMsgGet()

    val call: Call<List<RecieveDataClass>?>? = retrofitAPI.getMsg()


    call!!.enqueue(object : Callback<List<RecieveDataClass>?> {

        override fun onResponse(call: Call<List<RecieveDataClass>?>, response: Response<List<RecieveDataClass>?>) {
            //Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show()
            val model: List<RecieveDataClass> = response.body()?: emptyList()

            loginViewModel.firstMsgGet= model as MutableList<RecieveDataClass>
            loginViewModel.isLoading.value = false
            navController.navigate(NavigationItems.Messages.route)
//            val resp =model
//                        getApiResult.value= resp.toString()
//            if (model != null) {
//                loginViewModel.firstMsgGet=model
//            }

        }

        override fun onFailure(call: Call<List<RecieveDataClass>?>, t: Throwable) {
            //getApiResult.value="error "+t.message
        }
    })
}

//number of users
private fun getChatHistory(
//    context: Context,
//    getApiResult: MutableState<String>,
    loginViewModel: LoginViewModel
) {
    val retrofitAPI = loginViewModel.getAllChats()

    val call: Call<List<GetChatsDataClass>?>? = retrofitAPI.getChats()


    call!!.enqueue(object : Callback<List<GetChatsDataClass>?> {

        override fun onResponse(call: Call<List<GetChatsDataClass>?>, response: Response<List<GetChatsDataClass>?>) {
            //Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show()
            val model: List<GetChatsDataClass> = response.body()?: emptyList()

            loginViewModel.allChats= model as MutableList<GetChatsDataClass>

//            val resp =model
//                        getApiResult.value= resp.toString()
//            if (model != null) {
//                loginViewModel.firstMsgGet=model
//            }

        }

        override fun onFailure(call: Call<List<GetChatsDataClass>?>, t: Throwable) {
            //getApiResult.value="error "+t.message
        }
    })
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    val title = loginViewModel.user_name
    getChatHistory(loginViewModel)
    val result = remember {
        mutableStateOf("")
    }
    val getApiResult = remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier.background(Color.Blue),
        topBar = {
            TopAppBar(
                title = { Text(text = "Chats") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "LogOut")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    postRoom(context, title, result, navController, loginViewModel)
                },
                backgroundColor = Purple200,
                contentColor = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .width(50.dp)
                    .height(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "")
            }
        },

        ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(690.dp)
                .background(Color.White)
                .padding(8.dp),


            ) {
            itemsIndexed(loginViewModel.allChats) { lastIndex, item ->
                val time = item.created.subSequence(11, 16)
                val cardName = item.title
                //if (item.people[lastIndex].person.username == "tarushi07") "yash07" else "tarushi07"

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            loginViewModel.isLoading.value = true
                            loginViewModel.chatId = item.id
                            loginViewModel.accesskey = item.access_key
                            getMsgHistory(context, getApiResult, loginViewModel, navController)

                        },
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = Color.White
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = cardName,
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = item.last_message.text,
                                style = MaterialTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.Gray
                            )
                        }
                        Text(
                            text = time.toString(),
                            style = MaterialTheme.typography.body1,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }

            }
        }
        Text(
            text = result.value,
        )

//            Button(
//                modifier = Modifier.padding(horizontal = 120.dp, vertical = 300.dp),
//                onClick = {
//                    getMsgHistory(context, getApiResult, loginViewModel)
//                    navController.navigate(NavigationItems.Messages.route)
//                }
//            ) {
//                Text(text = "Start messaging")
//            }

    }
    if (loginViewModel.isLoading.value) {
        LoadingView()
    }

}

