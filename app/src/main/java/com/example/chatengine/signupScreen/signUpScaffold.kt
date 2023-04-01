package com.example.chatengine.signupScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatengine.viewModel.MainViewModel
import com.example.chatengine.ui.theme.Purple200


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpScaffold(navController: NavHostController, mainViewModel: MainViewModel){
    Scaffold(
        // in scaffold we are specifying top bar.
        topBar = {

            // specifying background color to topAppBar
            TopAppBar(backgroundColor = Purple200,

                //specifying title to topAppBar
                title = {

                    // specifying title as a text
                    Text(
                        // on below line we are specifying text to display in top app bar.
                        text = "New Account",

                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White
                    )
                },

                //The button will take user back to previous screen
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Use NavHostController to handle navigation back
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )},

        ) {
        // Composable function defining UI of the sign up screen.
        SignUpScreen(navController,mainViewModel)
    }
}