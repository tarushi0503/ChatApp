import android.annotation.SuppressLint
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.chatengine.loginScreen.*
import com.example.chatengine.signupScreen.SignUpDataClass
import com.example.chatengine.signupScreen.SignUpScreen
import com.example.chatengine.signupScreen.signUpApiInterface
import com.example.chatengine.viewModel.MainViewModel
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import junit.framework.TestCase.assertEquals



//Login UI testing
@RunWith(AndroidJUnit4::class)
class LoginTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var navController: NavController


    val mainViewModel= MainViewModel()



    @Before
    fun setUp() {
//        MockitoAnnotations.openMocks(this)
        MockitoAnnotations.initMocks(this)
    }


    @SuppressLint("CheckResult")
    @Test
    fun testLoginScreen() {
        // Create an instance of the LoginScreen composable function
        composeTestRule.setContent {
            Login(navController, mainViewModel)
        }



        // Enter the username and password
        composeTestRule.onNodeWithTag("Enter your Username").performTextInput("tarushi07")
        composeTestRule.onNodeWithTag("Password").performTextInput("12345678")

        // Click on the login button
        composeTestRule.onNodeWithTag("Login_Button").performClick()
        composeTestRule.onNodeWithTag("loading").assertIsDisplayed()


    }
}





//Login api test
@RunWith(AndroidJUnit4::class)
class LoginInterfaceAPITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var loginInterfaceAPI: LoginInterfaceAPI


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        loginInterfaceAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginInterfaceAPI::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getUsers_success() = runBlocking {
        // Set up mock server response
        val responseBody = "{\"username\":\"Test\",\"secret\":\"123\",\"is_authenticated\":true}"
        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        // Make API call
        val call: Call<LoginDataClass?> = loginInterfaceAPI.getUsers()!!
        val response: Response<LoginDataClass?> = call.execute()

        // Verify that the correct request was made
        val request = mockWebServer.takeRequest()
        assertEquals("/users/me/", request.path)

        // Verify that the response was parsed correctly
        assertTrue(response.isSuccessful)
        val loginData = response.body()
        assertNotNull(loginData)
        assertEquals("Test", loginData!!.username)
        assertEquals("123", loginData?.secret)
        assertEquals(true, loginData.is_authenticated)
    }

    @Test
    fun getUsers_failure() = runBlocking {
        // Set up mock server response
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        // Make API call
        val call: Call<LoginDataClass?> = loginInterfaceAPI.getUsers()!!
        val response: Response<LoginDataClass?> = call.execute()

        // Verify that the correct request was made
        val request = mockWebServer.takeRequest()
        assertEquals("/users/me/", request.path)

        // Verify that the response was not successful
        assertFalse(response.isSuccessful)
    }

    @Test
    fun getUsers_mockito() = runBlocking {
        // Set up mock response using Mockito
        val mockLoginData = LoginDataClass("Test", "123", false)
        val mockResponse: Response<LoginDataClass?> = Response.success(mockLoginData)

//        val mockCall = Mockito.mock(Call::class.java) as Call<LoginData?>
//        Mockito.`when`(mockCall.execute()).thenReturn(mockResponse)
//        Mockito.`when`(authenticationApi.getUsers()).thenReturn(mockCall)
        // Define mock objects
        val mockCall = Mockito.mock(Call::class.java) as Call<LoginDataClass?>
//        val mockResponse = Mockito.mock(Response::class.java) as Response<LoginData?>

        // Define mock behavior
        Mockito.`when`(mockCall.execute()).thenReturn(mockResponse)

        // Mock the authenticationApi object
        val authenticationApi = Mockito.mock(LoginInterfaceAPI::class.java)
        Mockito.`when`(authenticationApi.getUsers()).thenReturn(mockCall)

        // Make API call
        val response: Response<LoginDataClass?> = authenticationApi.getUsers()!!.execute()

        // Verify that the correct request was made
        verify(authenticationApi).getUsers()

        // Verify that the response was parsed correctly
        assertTrue(response.isSuccessful)
        val loginData = response.body()
        assertNotNull(loginData)
        assertEquals("Test", loginData?.username)

        assertEquals("123", loginData?.secret)
        assertEquals(false, loginData?.is_authenticated)
    }

}




//Sign up UI testing
@RunWith(AndroidJUnit4::class)
class SignUpScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var navController: NavController

    val mainViewModel= MainViewModel()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }


    @SuppressLint("CheckResult")
    @Test
    fun testSignUpScreen() {
        // Create an instance of the SignUpScreen composable function
        composeTestRule.setContent {
            SignUpScreen(navController, mainViewModel)
        }



        // Enter the username and password
        composeTestRule.onNodeWithTag("Enter your Username").performTextInput("tarushi07")
        composeTestRule.onNodeWithTag("Enter your first name").performTextInput("t")
        composeTestRule.onNodeWithTag("Enter your last name").performTextInput("t")
        composeTestRule.onNodeWithTag("Password").performTextInput("12345678")

        // Click on the login button
        composeTestRule.onNodeWithTag("SignUp_Button").performClick()
//        composeTestRule.onNodeWithTag("loading").assertIsDisplayed()

    }
}




//sign up API testing
@RunWith(AndroidJUnit4::class)
class SignUpApiInterfaceTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var signUpApi: signUpApiInterface

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        signUpApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(signUpApiInterface::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun postData_success() = runBlocking {
        // Create a SignUpDataClass object with the required data
        val signUpData = SignUpDataClass("tarushi07", "tt", "t", "12345678")

        // Set up mock server response
        val responseBody = "{\"username\":\"tarushi07\",\"first_name\":\"tt\",\"last_name\":\"t\",\"secret\":\"12345678\"}"
        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        // Make API call
        val call: Call<SignUpDataClass?> = signUpApi.postData(signUpData)!!
        val response: Response<SignUpDataClass?> = call.execute()

        // Verify that the correct request was made
        val request = mockWebServer.takeRequest()
        assertEquals("POST", request.method)
        assertEquals("/users/", request.path)

        // Verify that the request body contains the correct data
        val requestJson = request.body.readUtf8()
        assertEquals("{\"first_name\":\"tt\",\"last_name\":\"t\",\"secret\":\"12345678\",\"username\":\"tarushi07\"}", requestJson)

        // Verify that the response was parsed correctly
        assertTrue(response.isSuccessful)
        val signUpDataResponse = response.body()
        assertNotNull(signUpDataResponse)
        assertEquals("tarushi07", signUpDataResponse!!.username)
        assertEquals("tt", signUpDataResponse!!.first_name)
        assertEquals("t", signUpDataResponse!!.last_name)
        assertEquals("12345678", signUpDataResponse?.secret)

    }



    @Test
    fun postData_failure() = runBlocking {
        // Set up mock server response
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        // Make API call
        val call: Call<SignUpDataClass?> = signUpApi.postData(SignUpDataClass("","tt","t","12345678"))!!
        val response: Response<SignUpDataClass?> = call.execute()

        // Verify that the correct request was made
        val request = mockWebServer.takeRequest()
        assertEquals("/users/", request.path)

        // Verify that the response was not successful
        assertFalse(response.isSuccessful)
    }



    @Test
    fun postData_mockito() = runBlocking {
        // Set up mock response using Mockito
        val mockSignUpData = SignUpDataClass("Test", "t", "t","123")
        val mockResponse: Response<SignUpDataClass?> = Response.success(mockSignUpData)

        val mockCall = Mockito.mock(Call::class.java) as Call<SignUpDataClass?>

        // Define mock behavior
        Mockito.`when`(mockCall.execute()).thenReturn(mockResponse)

        // Mock the authenticationApi object
        val signUpApiInterface = Mockito.mock(signUpApiInterface::class.java)
        Mockito.`when`(signUpApiInterface.postData(mockSignUpData)).thenReturn(mockCall)

        // Make API call
        val response: Response<SignUpDataClass?> = signUpApiInterface.postData(mockSignUpData)!!.execute()

        // Verify that the correct request was made
        verify(signUpApiInterface).postData(mockSignUpData)

        // Verify that the response was parsed correctly
        assertTrue(response.isSuccessful)
        val loginData = response.body()
        assertNotNull(loginData)
        assertEquals("Test", loginData?.username)

        assertEquals("123", loginData?.secret)
    }

}









