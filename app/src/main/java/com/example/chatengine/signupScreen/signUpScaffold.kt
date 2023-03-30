package com.example.chatengine.signupScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
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

            // inside top bar we are specifying background color.
            TopAppBar(backgroundColor = Purple200,

                // along with that we are specifying title for our top bar.
                title = {

                    // in the top bar we are specifying tile as a text
                    Text(
                        // on below line we are specifying text to display in top app bar.
                        text = "New Account",


                        // on below line we are specifying modifier to fill max width.
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 75.dp),
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
            )},

        ) {
        // on the below line we are calling the pop window dialog method to display ui.
        SignUpScreen(navController,mainViewModel)
    }
}