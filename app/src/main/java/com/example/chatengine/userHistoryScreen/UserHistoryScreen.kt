package com.example.chatengine.userHistoryScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.chatengine.circularProgressIndicator.LoadingView
import com.example.chatengine.MainActivity
import com.example.chatengine.navigation.NavigationItems
import com.example.chatengine.R
import com.example.chatengine.viewModel.MainViewModel
import com.example.chatengine.messageScreen.RecieveDataClass
import com.example.chatengine.ui.theme.Purple200
import com.example.chatengine.ui.theme.card
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private fun getMsgHistory(
    context: Context,
    getApiResult: MutableState<String>,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    val retrofitAPI = mainViewModel.createMsgGet()

    val call: Call<List<RecieveDataClass>?>? = retrofitAPI.getMsg()


    call!!.enqueue(object : Callback<List<RecieveDataClass>?> {

        override fun onResponse(call: Call<List<RecieveDataClass>?>, response: Response<List<RecieveDataClass>?>) {
            //Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show()
            val model: List<RecieveDataClass> = response.body()?: emptyList()

            mainViewModel.firstMsgGet= model as MutableList<RecieveDataClass>
            mainViewModel.isLoading.value = false

        }

        override fun onFailure(call: Call<List<RecieveDataClass>?>, t: Throwable) {
            //getApiResult.value="error "+t.message
        }
    })
}

//number of users
 fun getChatHistory(
    mainViewModel: MainViewModel
    ) {
    val retrofitAPI = mainViewModel.getAllChats()

    val call: Call<List<GetChatsDataClass>?>? = retrofitAPI.getChats()


    call!!.enqueue(object : Callback<List<GetChatsDataClass>?> {

        override fun onResponse(call: Call<List<GetChatsDataClass>?>, response: Response<List<GetChatsDataClass>?>) {
            val model: List<GetChatsDataClass> = response.body()?: emptyList()

            mainViewModel.allChats= model as MutableList<GetChatsDataClass>
        }

        override fun onFailure(call: Call<List<GetChatsDataClass>?>, t: Throwable) {
            //getApiResult.value="error "+t.message
        }
    })
}


//This composable display the list of users with whom the room has been created
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserHistoryScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    sharedPreferences: SharedPreferences
) {

    //enables shared preferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
//    editor.putString("USERNAME",mainViewModel.username.value)
//    editor.putString("USERNAME",mainViewModel.password.value)
//    editor.apply()

    val context = LocalContext.current

    val getApiResult = remember {
        mutableStateOf("")
    }

    //function to fetch chat user data from api
    getChatHistory(mainViewModel)

    //it implements visual layout structure
    Scaffold(
        modifier = Modifier.background(Color.Blue),

        //it is a component of scaffold
        topBar = {
            TopAppBar(
                title = { Text(text = "Chats") },
                actions = {
                    //logs out user from app and clear backgroud activity
                    IconButton(onClick = {
                        editor.putString("USERNAME", "")
                        editor.putString("SECRET", "")
                        editor.apply()
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "LogOut")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavigationItems.QuestionsList.route)
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

        if((mainViewModel.allChats.size!=0)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(690.dp)
                    .background(Color.White)
                    .padding(8.dp),


                ) {
                itemsIndexed(mainViewModel.allChats) { lastIndex, item ->
                    val time = item.created.subSequence(11, 16)
//                    val cardName =
//                        if (mainViewModel.user_name.value == "tarushi07") item.title else "tarushi07"

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .clickable {
                                navController.navigate(NavigationItems.Messages.route)
                                mainViewModel.isLoading.value = true
                                mainViewModel.chatId = item.id
                                mainViewModel.accesskey = item.access_key
                                getMsgHistory(context, getApiResult, mainViewModel, navController)

                            },
                        elevation = 4.dp,
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = Color.White
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.user),
                                contentDescription = "user-image",
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp) // set the height of the image
                            )
                            Spacer(modifier = Modifier.width(10.dp))

                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = item.title,
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
        }
        else{
            Box(
                modifier = Modifier.height(550.dp)
                    .width(800.dp)
                    .padding(top=180.dp, start = 50.dp, end = 50.dp)
                    .background(card
                        ,shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center,
            ){
                Text(text = "No Chats Available",
                    fontSize = 20.sp)
            }
        }
    }
    if (mainViewModel.isLoading.value) {
        LoadingView()
    }

}

