package com.example.chatengine.loginScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.chatengine.Navigation.NavigationItems
import com.example.chatengine.R
import com.example.chatengine.ui.theme.Purple200
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private fun getDataUsingRetrofit(
    ctx: Context,
    result: MutableState<String>,
    secret: MutableState<String>,
    navController: NavController,
    loginViewModel: LoginViewModel
) {

    val retrofitAPI = loginViewModel.AuthenticateUser()

    val call: Call<LoginDataClass?>? = retrofitAPI.getUsers()


    call!!.enqueue(object : Callback<LoginDataClass?> {

        override fun onResponse(call: Call<LoginDataClass?>?, response: Response<LoginDataClass?>) {
            Toast.makeText(ctx, "Logged in", Toast.LENGTH_SHORT).show()
            val model: LoginDataClass? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n"+"Id: " + model?.is_authenticated+  "\n"+ model?.username
            result.value=resp
            secret.value = model?.secret.toString()
            loginViewModel.UserData=model
            if(model?.is_authenticated==true){
                navController.navigate(NavigationItems.UserScreen.route)
                Toast.makeText(ctx,"Logged in",Toast.LENGTH_LONG).show()
            }
            println("/////////////////////////////////////////////////////${secret.value}")

        }

        override fun onFailure(call: Call<LoginDataClass?>?, t: Throwable) {
            result.value="error "+t.message
        }
    })
}


@Composable
fun getDataLogin(navController: NavController,loginViewModel: LoginViewModel) {
    val context= LocalContext.current

    val userName = remember {
        mutableStateOf("")
    }
    val password = remember{
        mutableStateOf("")
    }

    val secret = remember {
        mutableStateOf("")
    }
    val result = remember {
        mutableStateOf("")
    }

    // on below line we are creating a column.
    Box(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, Color.Blue),
                    startY = 100f,
                    endY = 5000f
                )
            )
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

//        Image(
//            painter = painterResource(id = R.drawable.background),
//            contentDescription = "",
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.FillBounds
//        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                //.alpha(0.9f)
                .padding(16.dp),
            backgroundColor = Color.LightGray,
            shape = RoundedCornerShape(50.dp),
            elevation = 8.dp

        ) {

//            Image(
//                painter = painterResource(id = R.drawable.cardbackground), contentDescription = "",
//                modifier = Modifier.fillMaxWidth()
//                    .height(200.dp),
//                contentScale = ContentScale.Crop,
//            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
                    .fillMaxWidth(),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.icon), contentDescription = "",
                    modifier = Modifier.width(100.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = "Welcome Back to Rapi Chat",
                    color = Purple200,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(50.dp))


                OutlinedTextField(
                    value = userName.value,
                    onValueChange = { userName.value = it },
                    placeholder = { Text(text = "Enter your Username") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                    singleLine = true,
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Filled.Face,
                                contentDescription = "UserName Icon",
                                tint = Color.Blue
                            )
                        }
                    },
                )

                Spacer(modifier = Modifier.height(5.dp))
                // on below line we are creating a text field for our email.

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    placeholder = { Text(text = "Enter your Password") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                    singleLine = true,
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Filled.Lock,
                                contentDescription = "UserName Icon",
                                tint = Color.Blue
                            )
                        }
                    },
                )

                loginViewModel.user_name=userName.value
                loginViewModel.password= password.value


                Spacer(modifier = Modifier.height(10.dp))
                // on below line we are creating a button
                Button(
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        getDataUsingRetrofit(context,result,secret,navController,loginViewModel)
                    },
                    // on below line we are adding modifier to our button.
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // on below line we are adding text for our button
                    Text(text = "Login",fontWeight = FontWeight.Bold)
                }
                Text(text = result.value)
                // on below line we are adding a spacer.
                Spacer(modifier = Modifier.height(20.dp))

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Text(text = "Create an Account")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "SignUp", color = Color.Blue,
                        modifier = Modifier.clickable {
                            navController.navigate(NavigationItems.PostDataSignUpScaffold.route)
                        })
                }

            }
        }
    }
}