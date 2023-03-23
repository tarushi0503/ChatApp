package com.example.chatengine.userScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserScreen(navController: NavHostController) {
    Scaffold(

        topBar= {
            TopAppBar {
                Text(text = "User")
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Do something */ },
                backgroundColor = Color.Blue,
                contentColor = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .width(50.dp)
                    .height(48.dp)
            ) {
                Icon( Icons.Default.Add, contentDescription = "")
            }
        }

    ) {


    }
}