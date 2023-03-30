package com.example.chatengine.loginScreen

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//const val url = "https://api.chatengine.io/users/"
interface LoginInterfaceAPI {

    @GET("users/me/")
    fun getUsers(): Call<LoginDataClass?>?
}

class LoginClass(username:String,password:String){

    var username=username
    var password=password


    fun getInstance(): LoginInterfaceAPI{
        val loggingInterceptor=HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient=OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Project-ID", "0ddfa87c-cd5d-4103-8946-0b0ccc96cf9e")
                    .addHeader("User-name", username)
                    .addHeader("User-Secret", password)
                    //.addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit=Retrofit.Builder()
            .baseUrl("https://api.chatengine.io/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(LoginInterfaceAPI::class.java)

        return  retrofit!!
    }
}