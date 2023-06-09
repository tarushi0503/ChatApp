package com.example.chatengine.signupScreen

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.platform.testTag
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
import com.example.chatengine.circularProgressIndicator.LoadingView
import com.example.chatengine.navigation.NavigationItems
import com.example.chatengine.viewModel.MainViewModel
import com.example.chatengine.ui.theme.Purple200
import com.example.chatengine.ui.theme.card
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//function to post data to API
private fun signUpData(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    firstName: MutableState<TextFieldValue>,
    lastName: MutableState<TextFieldValue>,
    password: MutableState<TextFieldValue>,
    result: MutableState<String>,
    mainViewModel: MainViewModel
) {

    //stores the response of signup function created in main view model
    val retrofitAPI = mainViewModel.signup()

    //the signUpData receives parameters input by user and passes it to data class
    val signupDataClass=SignUpDataClass(userName.value.text,
        firstName.value.text,
        lastName.value.text,
        password.value.text)


    //represents an call to post data to the server using the postData() method defined in the signUpApiInterface
    val call: Call<SignUpDataClass?>? = retrofitAPI.postData(signupDataClass)

    call!!.enqueue(object : Callback<SignUpDataClass?> {
        override fun onResponse(
            call: Call<SignUpDataClass?>,
            response: Response<SignUpDataClass?>
        ) {
                //on sign Up the toast is shown
                Toast.makeText(ctx, "Sign up successful", Toast.LENGTH_SHORT).show()
        }

        override fun onFailure(call: Call<SignUpDataClass?>, t: Throwable) {
            result.value = "Error found is : " + t.message
        }
    })
}




//Composable for Signup screen
@Composable
fun SignUpScreen(navController:NavController, mainViewModel: MainViewModel) {
    val context = LocalContext.current

    //stores the changing values of outlined text field for taking username input
    val userName = remember {
        mutableStateOf(TextFieldValue())
    }

    //stores the changing values of outlined text field for taking first name input
    val firstName = remember {
        mutableStateOf(TextFieldValue())
    }

    //stores the changing values of outlined text field for taking last name input
    val lastName = remember {
        mutableStateOf(TextFieldValue())
    }

    //stores the changing values of outlined text field for taking password input
    val password = remember {
        mutableStateOf(TextFieldValue())
    }

    //stores the response of the api which helps in checking the success and failure rate of the post api
    val response = remember {
        mutableStateOf("")
    }

    //The is set to true on icon click below when user wants to see the typed password else remains false,i.e., invisible
    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    //only if all the credentials in the singup page will be filled, the signup button will change colour become enabled otherwise, it remains disabled
    val isCredentialsFilled = userName.value.text.isNotBlank() && password.value.text.isNotBlank() && firstName.value.text.isNotBlank() && lastName.value.text.isNotBlank()

    // on below line a box is created that has a gradient background colour to it
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

        //all the contents in the box will be aligned to centre
        contentAlignment = Alignment.Center
    ) {

        // on below line a card is created inside the box
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .verticalScroll(rememberScrollState())
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
                    painter = painterResource(id = com.example.chatengine.R.drawable.icon),
                    //contentDescription tell about the image
                    contentDescription = "logo",
                    //modifier enhances the look and feel of component
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .padding(top = 5.dp),
                    contentScale = ContentScale.Crop,
                )

                //It adds space between elements
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Welcome to",
                    color = Purple200,
                    fontSize = 19.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                )

                //It adds space between elements
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Nye Interactive Assistant",
                    color = Purple200,
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center
                )

                //It adds space between elements
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "(NIA)",
                    color = Purple200,
                    fontSize = 19.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                )

                //It adds space between elements
                Spacer(modifier = Modifier.height(15.dp))


                //this textfield stores the username entered by user
                OutlinedTextField(

                    value = userName.value,
                    onValueChange = { userName.value = it },
                    placeholder = { Text(text = "Enter your Username") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .testTag("Enter your Username"),
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

                // on below line we are creating a text field for first name.
                OutlinedTextField(

                    // on below line we are specifying value for our first name text field.
                    value = firstName.value,

                    // on below line we are adding on value change for text field.
                    onValueChange = { firstName.value = it },

                    // on below line we are adding place holder as text
                    placeholder = { Text(text = "Enter your First Name") },

                    // on below line we are adding modifier to it
                    // and adding padding to it and filling max width
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .testTag("Enter your first name"),

                    // on below line we are adding text style
                    // specifying color and font size to it.
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

                    // on below line we ar adding single line to it.
                    singleLine = true,

                    //adding icon to enhance UI
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = "UserName Icon",
                                tint = Color.Blue
                            )
                        }
                    },
                )

                // on below line we are specifying value for our last name text field.
                OutlinedTextField(

                    value = lastName.value,

                    // on below line we are adding on value change for text field.
                    onValueChange = { lastName.value = it },

                    // on below line we are adding place holder as text
                    placeholder = { Text(text = "Enter your Last Name") },

                    // on below line we are adding modifier to it
                    // and adding padding to it and filling max width
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .testTag("Enter your last name"),

                    // on below line we are adding text style
                    // specifying color and font size to it.
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

                    // on below line we ar adding single line to it.
                    singleLine = true,

                    //adding icon to enhance UI
                    leadingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Filled.AccountCircle,
                                contentDescription = "UserName Icon",
                                tint = Color.Blue
                            )
                        }
                    },
                )


                // on below line we are specifying value for our password text field.
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },

                    // on below line we are adding place holder as text
                    placeholder = { Text(text = "Enter your Password") },

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

                    // on below line we are adding modifier to it
                    // and adding padding to it and filling max width
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .testTag("Password"),

                    // on below line we are adding text style
                    // specifying color and font size to it.
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
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


                Spacer(modifier = Modifier.height(10.dp))
                // on below line we are creating a button

                ///Login button
                Button(
                    shape = RoundedCornerShape(10.dp),
                    onClick = {

                        //below valiadtions are put on client side to allow user to input only appropriate credentials
                        //the below check display toast if the credentials are empty
                        if(TextUtils.isEmpty(userName.value.text) || TextUtils.isEmpty(firstName.value.text) || TextUtils.isEmpty(lastName.value.text) || TextUtils.isEmpty(password.value.text)){
                            Toast.makeText(context,"Credentials empty",Toast.LENGTH_SHORT).show()
                        }

                        //the below check display toast if the username contains any special character
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

                        //the below check display toast if the first name contains and digit
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
                            Toast.makeText(context,"Digits not allowed in First Name",Toast.LENGTH_SHORT).show()
                        }

                        //the below check display toast if the last name contains and digit
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
                            Toast.makeText(context,"Digits not allowed in Last Name",Toast.LENGTH_SHORT).show()
                        }

                        //the below check display toast if the password lenght is less than 6
                        else if(password.value.text.length<=5){
                            Toast.makeText(context,"Passowrd length less than 8",Toast.LENGTH_SHORT).show()
                        }


                        //if all checks are passed
                        else{

                            //this to display loader while the data is being fetched from api
                            mainViewModel.isLoading.value = true

                            //this functions is called to create new user and post it to api
                            signUpData(
                                context, userName, firstName, lastName, password, response,mainViewModel
                            )

                            //after successful created, route to login screen
                            navController.navigate(NavigationItems.LoginScreen.route)

                            //this stops displaying loader
                            mainViewModel.isLoading.value = false
                        }


                    },

                    //the state of where all credentials are filled of not is assigned to enabled
                    enabled = isCredentialsFilled,
                    // on below line we are adding modifier to our button.
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag("SignUp_Button")
                ) {
                    // on below line we are adding text for our button
                    Text(text = "SignUp", fontWeight = FontWeight.Bold)
                }

                // on below line we are adding a spacer.
                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }
    //if the value is true, it displayes loader
    if (mainViewModel.isLoading.value){
        LoadingView()
    }
}





