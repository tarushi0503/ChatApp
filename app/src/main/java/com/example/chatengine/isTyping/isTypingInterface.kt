package com.example.chatengine.isTyping

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

interface isTypingInterface {

    @POST("typing/")
    fun notifyTyping(): Call<isTypingDataClass?>?
}


class TypingClass(val username:String, val password:String,val chatId:String) {


    fun getTypingInstance(): isTypingInterface {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val url = "https://api.chatengine.io/chats/${chatId}/"

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Project-ID", "0ddfa87c-cd5d-4103-8946-0b0ccc96cf9e")
                    .addHeader("User-Name", username)
                    .addHeader("User-Secret", password)
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(isTypingInterface::class.java)
    }
}