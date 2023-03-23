package com.example.chatengine.signupScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatengine.Navigation.NavigationItems
import com.example.chatengine.loginScreen.getDataLogin
import com.example.chatengine.ui.theme.Purple200
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



private fun postDataUsingRetrofit(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    firstName: MutableState<TextFieldValue>,
    lastName: MutableState<TextFieldValue>,
    password: MutableState<TextFieldValue>,
    result: MutableState<String>
) {
    val retrofitAPI = RetrofitAPI.postInstance()

    val dataModel = DataModel(
        userName.value.text,
        firstName.value.text,
        lastName.value.text,
        password.value.text,
        email = ""
    )

    val call: Call<DataModel?>? = retrofitAPI.postData(dataModel)

    call!!.enqueue(object : Callback<DataModel?> {
        override fun onResponse(call: Call<DataModel?>?, response: Response<DataModel?>) {
            Toast.makeText(ctx, "Data posted to API", Toast.LENGTH_SHORT).show()
            val model: DataModel? = response.body()
            val resp =
                "Response Code : " + response.code()
            result.value = resp
        }

        override fun onFailure(call: Call<DataModel?>?, t: Throwable) {
            result.value = "Error found is : " + t.message
        }
    })
}




@Composable
fun postData(navController:NavController) {
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
    val password = remember{
        mutableStateOf(TextFieldValue())
    }
    val response = remember {
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
                // on below line we are creating a text
                Text(
                    text = "Retrofit POST Request in Android",
                    color = Purple200,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(5.dp))

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
                                Icons.Filled.Face,
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
                    onClick =  {
                        // on below line we are calling make payment method to update data.
                        postDataUsingRetrofit(
                            ctx, userName, firstName, lastName, password, response
                        )
                        navController.navigate(NavigationItems.getDataLogin.route)
                    },
                    // on below line we are adding modifier to our button.
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // on below line we are adding text for our button
                    Text(text = "SignUp",fontWeight = FontWeight.Bold)
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
}






