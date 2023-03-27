package com.example.chatengine.userScreen

import com.example.chatengine.messageScreen.MsgDataClassModel
import com.example.chatengine.messageScreen.RecieveDataClass
import com.example.chatengine.loginScreen.url
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface GetMyChats {

    @GET("/chats")
    fun getChats(): Call<List<GetChatsDataClass>?>?
}

class GetMyChatsClass(username:String,password:String){

    var username=username
    var password=password


    fun getMsgInstance(): GetMyChats {
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
            .baseUrl(url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create( GetMyChats::class.java)

        return  retrofit!!
    }
}

