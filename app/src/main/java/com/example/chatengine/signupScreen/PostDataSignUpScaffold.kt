package com.example.chatengine.signupScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.chatengine.ui.theme.Purple200


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PostDataSignUpScaffold(navController: NavHostController){
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
                        text = "Retrofit POST Request in Android",

                        // on below line we are specifying modifier to fill max width.
                        modifier = Modifier.fillMaxWidth(),

                        // on below line we are specifying text alignment.
                        textAlign = TextAlign.Center,

                        // on below line we are specifying color for our text.
                        color = Color.White
                    )
                })}
        ) {
        // on the below line we are calling the pop window dialog method to display ui.
        postData(navController)
    }
}