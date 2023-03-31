package com.example.chatengine


import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import com.example.chatengine.navigation.NavigationController
import com.example.chatengine.questionsRoom.QuestionsListing
import com.example.chatengine.questionsRoom.QuestionsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//MainActivity class that extends ComponentActivity
class MainActivity : ComponentActivity() {

    /*
    * initialization of SharedPreferences instance for maintaining logging logout sessions
    * initialization of a QuestionsViewModel instance using viewModels.
     */
    lateinit var sharedPreferences:SharedPreferences
    val questionViewModel by viewModels<QuestionsViewModel>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                QuestionsListing(questionViewModel)
            }
        }
        super.onCreate(savedInstanceState)

        //setContent method is used to set the content view for the activity
        setContent {

            /*
            * initialization of username and password as mutable state variables using remember.
            * Then, it retrieves values for username and pwd from the SharedPreferences instance.
            */
            sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
            val username = remember {
                mutableStateOf("")
            }
            val pwd = remember {
                mutableStateOf("")
            }
            username.value = sharedPreferences.getString("USERNAME", "").toString()
            pwd.value = sharedPreferences.getString("SECRET", "").toString()

            //NavigationController composable function  is called and SharedPreferences instance is passed.
            NavigationController(sharedPreferences)
        }
    }
}




