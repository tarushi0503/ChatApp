package com.example.chatengine.messageScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Messages(){

    var message= remember {
        mutableStateOf("")
    }

    Box(
        modifier= Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        Row(
            modifier= Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = message.value, onValueChange = {
                    message.value=it
                })
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Send, contentDescription = "")

            }
        }

    }
}