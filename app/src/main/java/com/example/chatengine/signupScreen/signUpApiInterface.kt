package com.example.chatengine.signupScreen


import com.example.chatengine.constants.baseUrl
import com.example.chatengine.constants.privateKey
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


/*the code defines an API interface and a class that use Retrofit to make network requests to a server
 for posting sign up data with a username and password*/
interface signUpApiInterface {

    //specifies that the postData() method is making an HTTP POST request to the server with the "users/" endpoint.
    @POST("users/")
    fun postData(@Body signUpDataClass: SignUpDataClass?): Call<SignUpDataClass?>?
}

class SignUpClass(var username: String, var password: String) {


    /*postInstance() is a method that returns an instance of signUpApiInterface using the Retrofit.Builder()
    while making network requests.*/
    fun postInstance(): signUpApiInterface {

        /*loggingInterceptor is an instance of HttpLoggingInterceptor used to log
        the HTTP requests and responses.*/
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


        /*httpClient is an instance of OkHttpClient that configures the HTTP
        client*/
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("PRIVATE-KEY", privateKey)
                    .build()
                chain.proceed(request)
            }
            .build()

        //creates a Retrofit instance, specifying the base URL, the HTTP client, and the JSON converter factory to use
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(signUpApiInterface::class.java)
    }
}
