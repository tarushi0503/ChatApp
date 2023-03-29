package com.example.chatengine.TEST


import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModelProvider


@Composable
fun MyApp() {
    val context = LocalContext.current
    val viewModel = viewModel<MyViewModel>(factory = MyViewModelFactory(context))
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    if (isLoggedIn) {
        HomeScreen(onLogoutClick = { viewModel.logout() })
    } else {
        LoginScreen(onLoginClick = { viewModel.login() })
    }
}

@Composable
fun LoginScreen(onLoginClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}

@Composable
fun HomeScreen(onLogoutClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}

class MyViewModel(private val context: Context) : ViewModel() {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
    }

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    init {
        _isLoggedIn.value = sharedPreferences.getBoolean("is_logged_in", false)
    }

    fun login() {
        sharedPreferences.edit().putBoolean("is_logged_in", true).apply()
        _isLoggedIn.value = true
    }

    fun logout() {
        sharedPreferences.edit().putBoolean("is_logged_in", false).apply()
        _isLoggedIn.value = false
    }
}

class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

