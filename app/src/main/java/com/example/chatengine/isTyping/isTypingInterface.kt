package com.example.chatengine.isTyping

import com.example.chatengine.constants.constants.projectId
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST


/*the code defines an API interface and a class that use Retrofit to make network requests to a server */
interface isTypingInterface {

    //specifies that the notifyTyping() method is making an HTTP POST request to the server with the specified endpoint.
    @POST("typing/")
    fun notifyTyping(): Call<isTypingDataClass?>?
}


class TypingClass(val username:String, val password:String,val chatId:String) {


    /*getTypingInstance() is a method that returns an instance of isTypingInterface using the Retrofit.Builder()
   while making network requests.*/
    fun getTypingInstance(): isTypingInterface {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val url = "https://api.chatengine.io/chats/${chatId}/"

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Project-ID", projectId)
                    .addHeader("User-Name", username)
                    .addHeader("User-Secret", password)
                    .build()
                chain.proceed(request)
            }
            .build()

        //creates a Retrofit instance, specifying the base URL, the HTTP client, and the JSON converter factory to use
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(isTypingInterface::class.java)
    }
}