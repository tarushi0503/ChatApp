package com.example.chatengine.loginScreen

import android.content.Context
import android.content.SharedPreferences
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatengine.circularProgressIndicator.LoadingView
import com.example.chatengine.navigation.NavigationItems
import com.example.chatengine.R
import com.example.chatengine.ui.theme.Purple200
import com.example.chatengine.ui.theme.Purple500
import com.example.chatengine.ui.theme.card
import com.example.chatengine.userHistoryScreen.getChatHistory
import com.example.chatengine.viewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//function to get data from API
private fun loginData(
    ctx: Context,
    username:String,
    password:String,
    result: MutableState<String>,
    secret: MutableState<String>,
    navController: NavController,
    mainViewModel: MainViewModel,
    sharedPreferences: SharedPreferences
) {

    //stores the response of AuthenticateUser() function created in main view model
    val retrofitAPI = mainViewModel.AuthenticateUser()


    //represents an call to get data from the server
    val call: Call<LoginDataClass?>? = retrofitAPI.getUsers()

    //for shared preferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()


    call!!.enqueue(object : Callback<LoginDataClass?> {

        override fun onResponse(call: Call<LoginDataClass?>, response: Response<LoginDataClass?>) {
            Toast.makeText(ctx, "Logged in", Toast.LENGTH_SHORT).show()
            val model: LoginDataClass? = response.body()
            secret.value = model?.secret.toString()
            mainViewModel.UserData=model

            //only when is_authenticated is true the user wil navigate else won't
            if(model?.is_authenticated==true){
                navController.navigate(NavigationItems.UserHistoryScreen.route)
                mainViewModel.isLoading.value=false
            }

            //on pressing Login button the chat history is fetched and credentials of user are stored in shared preferences
            if(response.isSuccessful){
                getChatHistory(mainViewModel)
                editor.putString("USERNAME", username)
                editor.putString("SECRET", password)
                editor.apply()
            }

        }

        override fun onFailure(call: Call<LoginDataClass?>, t: Throwable) {
            result.value="error "+t.message
        }
    })
}


//composable for login screen
@Composable
fun LoginScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    sharedPreferences: SharedPreferences
) {
    val context= LocalContext.current

    //stores the changing values of outlined text field for taking username input
    val userName = mainViewModel.username

    //stores the changing values of outlined text field for taking password
    val password = mainViewModel.password

    val secret = remember {
        mutableStateOf("")
    }

    //stores api response
    val result = remember {
        mutableStateOf("")
    }


    //The is set to true on icon click below when user wants to see the typed password else remains false,i.e., invisible
    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    //only if all the credentials in the singup page will be filled, the signup button will change colour become enabled otherwise, it remains disabled
    val isCredentialsFilled = userName.value.isNotBlank() && password.value.isNotBlank()


    //create two variables to enable shared preferences
    val userNameSharedPreferences = sharedPreferences.getString("USERNAME", "").toString()
    val passwordSharedPreferences = sharedPreferences.getString("SECRET", "").toString()

    //if shared preferences already has data, user won't be asked to login again
    if (userNameSharedPreferences.isNotBlank()){
        mainViewModel.username.value = userNameSharedPreferences
        mainViewModel.password.value = passwordSharedPreferences
        loginData(context,userNameSharedPreferences,passwordSharedPreferences,result,secret,navController,mainViewModel,sharedPreferences)
    }

    //else user will be asked to login again
    else {
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

            // on below line a card is created inside the box
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

                // on below line a column is created inside the card
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                        .fillMaxWidth(),

                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // on below line a image is created inside the column
                    Image(

                        //painter stores the location of the image
                        painter = painterResource(id = R.drawable.icon),
                        //contentDescription tell about the image
                        contentDescription = "",
                        //modifier enhances the look and feel of component
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp),
                        contentScale = ContentScale.Crop,
                    )

                    //It adds space between elements
                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "Welcome Back to",
                        color = Purple200,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                    )

                    //It adds space between elements
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Nye Interactive Assistant",
                        color = Purple200,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center
                    )

                    //It adds space between elements
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "(NIA)",
                        color = Purple200,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                    )


                    //It adds space between elements
                    Spacer(modifier = Modifier.height(30.dp))


                    //this textfield stores the username entered by user
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

                    //It adds space between elements
                    Spacer(modifier = Modifier.height(5.dp))

                    //this textfield stores the username entered by user
                    OutlinedTextField(

                        // on below line we are specifying value for our first name text field.
                        value = password.value,

                        // on below line we are adding on value change for text field.
                        onValueChange = { password.value = it },

                        //it keeps the passowrd hidden by default and makes it visible if passwordVisibility is false
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

                        //on clicking this icon the password becomes visible and invisible
                        trailingIcon = {
                            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                Icon(
                                    if (passwordVisibility) Icons.Filled.CheckCircle else Icons.Filled.Lock,
                                    contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                                )
                            }
                        },

                        // on below line we are adding place holder as text
                        placeholder = { Text(text = "Enter your Password") },

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

                        //adding icon to enhance UI
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

                    mainViewModel.username = userName
                    mainViewModel.password = password


                    //It adds space between elements
                    Spacer(modifier = Modifier.height(10.dp))
                    // on below line we are creating a button
                    Button(
                        onClick = {
                            //the below check display toast if the credentials are empty
                            if (userName.value == "" || password.value == "") {
                                Toast.makeText(
                                    context,
                                    "Credentials incorrect or empty",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {

                                //this to display loader while the data is being fetched from api
                                mainViewModel.isLoading.value = true

                                //this functions is called to get data from api
                                loginData(
                                    context,
                                    userName.value,
                                    password.value,
                                    result,
                                    secret,
                                    navController,
                                    mainViewModel,
                                    sharedPreferences
                                )
                            }
                        },

                        //the state of where all credentials are filled of not is assigned to enabled
                        enabled = isCredentialsFilled,

                        // on below line we are adding modifier to our button.
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Purple500,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(70.dp),


                        ) {
                        // on below line we are adding text for our button
                        Text(text = "Login", fontWeight = FontWeight.Bold)
                    }
                    Text(text = result.value)
                    // on below line we are adding a spacer.
                    Spacer(modifier = Modifier.height(20.dp))

                    Spacer(modifier = Modifier.height(20.dp))

                    //if user is new, user can go to signup page
                    Row {
                        Text(text = "Create an Account")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "SignUp", color = Color.Blue,
                            modifier = Modifier.clickable {
                                navController.navigate(NavigationItems.SignUpScaffold.route)
                            })
                    }

                }
            }
        }
    }
    if (mainViewModel.isLoading.value){
        LoadingView()
    }
}