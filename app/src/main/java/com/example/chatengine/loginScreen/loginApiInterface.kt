package com.example.chatengine.loginScreen

import com.example.chatengine.constants.baseUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/*the code defines an API interface and a class that use Retrofit
to make network requests to a server for getting login data with a username and password.*/
interface LoginInterfaceAPI {

    //gets the data with the specified end point
    @GET("users/me/")
    fun getUsers(): Call<LoginDataClass?>?

}

class LoginClass(var username: String, var password: String){

    /*getInstance() is a method that returns an instance of loginAPIInterface using the Retrofit.Builder()
    while making network requests.*/
    fun getInstance(): LoginInterfaceAPI{

        /*loggingInterceptor is an instance of HttpLoggingInterceptor used to log
       the HTTP requests and responses.*/
        val loggingInterceptor=HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


        /*httpClient is an instance of OkHttpClient that configures the HTTP
        client*/
        val httpClient=OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Project-ID", "0ddfa87c-cd5d-4103-8946-0b0ccc96cf9e")
                    .addHeader("User-name", username)
                    .addHeader("User-Secret", password)
                    .build()
                chain.proceed(request)
            }
            .build()

        //creates a Retrofit instance, specifying the base URL, the HTTP client, and the JSON converter factory to use
        val retrofit=Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(LoginInterfaceAPI::class.java)

        return  retrofit!!
    }
}