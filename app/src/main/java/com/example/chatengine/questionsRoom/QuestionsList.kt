package com.example.chatengine.questionsRoom

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.chatengine.navigation.NavigationItems
import com.example.chatengine.questionsScreen.ChatDataClass
import com.example.chatengine.ui.theme.Purple500
import com.example.chatengine.viewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun QuestionsList(navController: NavController, mainViewModel: MainViewModel,questionViewModel: QuestionsViewModel) {
    val expandedQuestion = remember { mutableStateOf<Questions?>(null) }

    val questions by questionViewModel.questions.observeAsState(emptyList())

    val context = LocalContext.current
    val title = mainViewModel.username

    val result = remember {
        mutableStateOf("")
    }


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


        LazyColumn(
            modifier = Modifier.padding(16.dp).
                    height(650.dp)
        ) {
            items(questions) { question ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { expandedQuestion.value = question },
                    elevation = 8.dp
                ) {
                    Text(
                        text = question.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }

                if (expandedQuestion.value == question) {
                    question.subQuestions.forEach { subQuestion ->
                        mainViewModel.chatName=subQuestion.question
                        SubQuestion(
                            subQuestion = subQuestion,
                            onSubQuestionClick = { expandedQuestion.value = question },
                            mainViewModel
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
                //.padding(10.dp),
            contentAlignment = Alignment.BottomCenter
        ) {

            Button(
                onClick = {
                    postRoom(context, title.value, result, navController, mainViewModel)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
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
    LaunchedEffect(Unit) {
        questionViewModel.loadQuestions()


    }
    }
}

@Composable
fun SubQuestion(
    subQuestion: SubQuestion,
    onSubQuestionClick: () -> Unit,
    mainViewModel: MainViewModel
) {
    var expandedSubQuestion by remember { mutableStateOf(false) }

//Text(text = subQuestion.subQuestions.toString())
    if(subQuestion.subQuestions?.isEmpty() == true){
        Row() {
            Icon(Icons.Default.ArrowForward, contentDescription = "Solution")
            Text(
                text = subQuestion.question,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
    else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .clickable {
                    expandedSubQuestion = !expandedSubQuestion
                    onSubQuestionClick()
                },
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = subQuestion.question,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (expandedSubQuestion) {
                    subQuestion.subQuestions?.forEach { nestedSubQuestion ->
                        mainViewModel.chatName=subQuestion.question
                        SubQuestion(
                            subQuestion = nestedSubQuestion,
                            onSubQuestionClick = onSubQuestionClick,
                            mainViewModel
                        )
                    }
                }
            }
        }
    }
}




private fun postRoom(
    ctx: Context,
    title: String,
    result: MutableState<String>,
    navController: NavController,
    mainViewModel: MainViewModel
) {

    val retrofitAPI = mainViewModel.createChat()
    val chatDataClass= ChatDataClass(mainViewModel.chatName,false, listOf("tarushi07"))

    val call: Call<ChatDataClass?>? = retrofitAPI.postChatRoom(chatDataClass)

    call!!.enqueue(object : Callback<ChatDataClass?> {

        override fun onResponse(call: Call<ChatDataClass?>, response: Response<ChatDataClass?>) {
            Toast.makeText(ctx, " in", Toast.LENGTH_SHORT).show()
            val model: ChatDataClass? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n"+"Id: " + model?.is_direct_chat+  "\n"+ model?.title
            result.value=resp
//            secret.value = model?.secret.toString()
            mainViewModel.newChatDetails = model
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