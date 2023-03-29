package com.example.chatengine.signupScreen

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatengine.CircularProgressIndicator.LoadingView
import com.example.chatengine.Navigation.NavigationItems
import com.example.chatengine.loginScreen.LoginViewModel
import com.example.chatengine.ui.theme.Purple200
import com.example.chatengine.ui.theme.card
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



private fun postDataUsingRetrofit(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    firstName: MutableState<TextFieldValue>,
    lastName: MutableState<TextFieldValue>,
    password: MutableState<TextFieldValue>,
    result: MutableState<String>,
    loginViewModel: LoginViewModel
) {
    val retrofitAPI = loginViewModel.signup()
    val signupDataClass=DataModel(userName.value.text,
        firstName.value.text,
        lastName.value.text,
        password.value.text,
        email = "")

    val call: Call<DataModel?>? = retrofitAPI.postData(signupDataClass)

    call!!.enqueue(object : Callback<DataModel?> {
        override fun onResponse(call: Call<DataModel?>?, response: Response<DataModel?>) {
            Toast.makeText(ctx, "Sign up successful", Toast.LENGTH_SHORT).show()
        }

        override fun onFailure(call: Call<DataModel?>?, t: Throwable) {
            result.value = "Error found is : " + t.message
        }
    })
}




@Composable
fun postData(navController:NavController,loginViewModel: LoginViewModel) {
    val ctx = LocalContext.current

    val userName = remember {
        mutableStateOf(TextFieldValue())
    }
    val firstName = remember {
        mutableStateOf(TextFieldValue())
    }
    val lastName = remember {
        mutableStateOf(TextFieldValue())
    }
    val password = remember {
        mutableStateOf(TextFieldValue())
    }
    val response = remember {
        mutableStateOf("")
    }
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val isCredentialsFilled = userName.value.text.isNotBlank() && password.value.text.isNotBlank() && firstName.value.text.isNotBlank() && lastName.value.text.isNotBlank()
    val context= LocalContext.current
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
            backgroundColor = card,
            shape = RoundedCornerShape(50.dp),
            elevation = 8.dp

        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
                    .fillMaxWidth(),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // on below line we are creating a text


                Image(
                    painter = painterResource(id = com.example.chatengine.R.drawable.icon),
                    contentDescription = "",
                    modifier = Modifier.width(80.dp)
                        .height(80.dp)
                        .padding(top = 5.dp),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Welcome to",
                    color = Purple200,
                    fontSize = 19.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Nye Interactive Assistant",
                    color = Purple200,
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "(NIA)",
                    color = Purple200,
                    fontSize = 19.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(15.dp))

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
                    // on below line we are specifying value for our email text field.
                    value = firstName.value,
                    // on below line we are adding on value change for text field.
                    onValueChange = { firstName.value = it },
                    // on below line we are adding place holder as text as "Enter your email"
                    placeholder = { Text(text = "Enter your First Name") },
                    // on below line we are adding modifier to it
                    // and adding padding to it and filling max width
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    // on below line we are adding text style
                    // specifying color and font size to it.
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                    // on below line we ar adding single line to it.
                    singleLine = true,
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Default.Face,
                                contentDescription = "UserName Icon",
                                tint = Color.Blue
                            )
                        }
                    },
                )
                OutlinedTextField(
                    // on below line we are specifying value for our email text field.
                    value = lastName.value,
                    // on below line we are adding on value change for text field.
                    onValueChange = { lastName.value = it },
                    // on below line we are adding place holder as text as "Enter your email"
                    placeholder = { Text(text = "Enter your Last Name") },
                    // on below line we are adding modifier to it
                    // and adding padding to it and filling max width
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    // on below line we are adding text style
                    // specifying color and font size to it.
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                    // on below line we ar adding single line to it.
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


                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    placeholder = { Text(text = "Enter your Password") },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                if (passwordVisibility) Icons.Filled.CheckCircle else Icons.Filled.Lock,
                                contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                            )
                        }
                    },
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
//            label = {
//                Text(text = "Password")
//            },
                )


                Spacer(modifier = Modifier.height(10.dp))
                // on below line we are creating a button
                Button(
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        if(TextUtils.isEmpty(userName.value.text) || TextUtils.isEmpty(firstName.value.text) || TextUtils.isEmpty(lastName.value.text) || TextUtils.isEmpty(password.value.text)){
                            Toast.makeText(ctx,"Credentials empty",Toast.LENGTH_SHORT).show()
                        }

                        else if(
                            userName.value.text.contains("@") ||
                            userName.value.text.contains(",") ||
                            userName.value.text.contains("!") ||
                            userName.value.text.contains("$") ||
                            userName.value.text.contains("%") ||
                            userName.value.text.contains("^") ||
                            userName.value.text.contains("&") ||
                            userName.value.text.contains(",") ||
                            userName.value.text.contains("(") ||
                            userName.value.text.contains(".") ||
                            userName.value.text.contains(")") ||
                            userName.value.text.contains("_") ||
                            userName.value.text.contains("+") ||
                            userName.value.text.contains("-")
                        ){
                            Toast.makeText(context,"Special character not allowed in username",Toast.LENGTH_SHORT).show()
                        }

                        else if(firstName.value.text.contains("1") ||
                            firstName.value.text.contains("2")||
                            firstName.value.text.contains("3")||
                            firstName.value.text.contains("4")||
                            firstName.value.text.contains("5")||
                            firstName.value.text.contains("6")||
                            firstName.value.text.contains("7")||
                            firstName.value.text.contains("8")||
                            firstName.value.text.contains("9")||
                            firstName.value.text.contains("0")){
                            Toast.makeText(ctx,"Digits not allowed in First Name",Toast.LENGTH_SHORT).show()
                        }

                        else if(
                            lastName.value.text.contains("1") ||
                            lastName.value.text.contains("2")||
                            lastName.value.text.contains("3")||
                            lastName.value.text.contains("4")||
                            lastName.value.text.contains("5")||
                            lastName.value.text.contains("6")||
                            lastName.value.text.contains("7")||
                            lastName.value.text.contains("8")||
                            lastName.value.text.contains("9")||
                            lastName.value.text.contains("0"))
                        {
                            Toast.makeText(ctx,"Digits not allowed in Last Name",Toast.LENGTH_SHORT).show()
                        }

                        else if(password.value.text.length<=5){
                            Toast.makeText(ctx,"Passowrd length less than 8",Toast.LENGTH_SHORT).show()
                        }


                        else{
                            loginViewModel.isLoading.value = true
                            postDataUsingRetrofit(
                                ctx, userName, firstName, lastName, password, response,loginViewModel
                            )
                            navController.navigate(NavigationItems.getDataLogin.route)
                            loginViewModel.isLoading.value = false
                        }


                    },
                    enabled = isCredentialsFilled,
                    // on below line we are adding modifier to our button.
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // on below line we are adding text for our button
                    Text(text = "SignUp", fontWeight = FontWeight.Bold)
                }
                // on below line we are adding a spacer.
                Spacer(modifier = Modifier.height(20.dp))
                // on below line we are creating a text for displaying a response.
//                Text(
//                    text = response.value,
//                    color = Color.Black,
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold, modifier = Modifier
//                        .padding(10.dp)
//                        .fillMaxWidth(),
//                    textAlign = TextAlign.Center
//                )

                Row {
                    Text(text = "Have an Account?")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Login", color = Color.Blue,
                        modifier = Modifier.clickable {
                            navController.navigate(NavigationItems.getDataLogin.route)
                        })
                }

                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }
    if (loginViewModel.isLoading.value){
        LoadingView()
    }
}





