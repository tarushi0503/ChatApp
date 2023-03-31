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
import androidx.navigation.NavController
import com.example.chatengine.navigation.NavigationItems
import com.example.chatengine.questionsRoom.room.QuestionsViewModel
import com.example.chatengine.ui.theme.Purple500
import com.example.chatengine.viewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun QuestionsList(navController: NavController, mainViewModel: MainViewModel,questionViewModel: QuestionsViewModel) {

    //stores list of questions that will be shown on card click
    val expandedQuestion = remember { mutableStateOf<Questions?>(null) }

    val questions by questionViewModel.questions.observeAsState(emptyList())

    val context = LocalContext.current
    val title = mainViewModel.username

    val result = remember {
        mutableStateOf("")
    }


    //implements visual layout structure
    Scaffold(
        topBar = {
            //inside scaffold, there is top bar
            TopAppBar(
                //title is the text displayed inside top bar
                title = { Text(text = "Admin")},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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

        //lazy column is used to display list of questions and further sub-questions on getting clicked
        LazyColumn(
            modifier = Modifier.padding(16.dp).
                    height(650.dp)
        ) {
            //displays ist of questions
            items(questions) { question ->

                //inside lazy column, there is card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { expandedQuestion.value = question },
                    elevation = 8.dp
                ) {
                    //card has text inside it
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

                //the card which is clicked its subquestions is displayed
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

        //Box contains button
        Box(
            modifier = Modifier
                .fillMaxSize(),
                //.padding(10.dp),
            contentAlignment = Alignment.BottomCenter
        ) {

            //chat room is created on button click using post room function
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
                //text that is displayed inside button
                Text(text = "Chat with Admin", fontWeight = FontWeight.Bold)
            }
        }
    LaunchedEffect(Unit) {
        questionViewModel.loadQuestions()


    }
    }
}


//composable displays question inside sub-question list
@Composable
fun SubQuestion(
    subQuestion: SubQuestion,
    onSubQuestionClick: () -> Unit,
    mainViewModel: MainViewModel
) {
    //contains questions inside sub-question
    var expandedSubQuestion by remember { mutableStateOf(false) }

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
        //below a card is created
        Card(
            //card has modifier properties to give width, padding and clickable
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .clickable {
                    expandedSubQuestion = !expandedSubQuestion
                    onSubQuestionClick()
                },
            elevation = 4.dp
        ) {

            //Card contains column
            Column(modifier = Modifier.padding(16.dp)) {

                //Column contains text which shows question inside sub question
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





//function to create chat room
private fun postRoom(
    ctx: Context,
    title: String,
    result: MutableState<String>,
    navController: NavController,
    mainViewModel: MainViewModel
) {

    //stores the response of createChat() function created in main view model
    val retrofitAPI = mainViewModel.createChat()

    //the roomDataClass receives parameters passes it to data class
    val roomDataClass= RoomDataClass(mainViewModel.chatName,false, listOf("tarushi07"))

    //represents a call to post data to the server using the postChatRoom() method defined in the RoomApiInterface
    val call: Call<RoomDataClass?>? = retrofitAPI.postChatRoom(roomDataClass)

    call!!.enqueue(object : Callback<RoomDataClass?> {

        override fun onResponse(call: Call<RoomDataClass?>, response: Response<RoomDataClass?>) {

            val model: RoomDataClass? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n"+"Id: " + model?.is_direct_chat+  "\n"+ model?.title
            result.value=resp
            mainViewModel.newChatDetails = model

            //if the value of is_direct_chhat is false, it navigates further
            if(model?.is_direct_chat==false){
                //on room creation the toast is shown
                Toast.makeText(ctx,"Chat created", Toast.LENGTH_LONG).show()
                navController.navigate(NavigationItems.UserHistoryScreen.route)
            }

        }

        override fun onFailure(call: Call<RoomDataClass?>, t: Throwable) {
            result.value="error "+t.message
        }
    })
}