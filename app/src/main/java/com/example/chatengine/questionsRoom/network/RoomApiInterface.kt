package com.example.chatengine.questionsScreen


import com.example.chatengine.constants.constants.baseUrl
import com.example.chatengine.constants.constants.projectId
import com.example.chatengine.questionsRoom.RoomDataClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PUT



/*the code defines an API interface and a class that use Retrofit
to make network requests to a server for ceate chat room and adding a default user */
interface RoomApiInterface {

    //gets the data an creates room with the specified end point
    @PUT("chats/")
    fun postChatRoom(@Body roomDataClass: RoomDataClass?): Call<RoomDataClass?>?
}

class ChatRoom(var username: String, var password: String){


    /*postRoomInstance() is a method that returns an instance interface using the Retrofit.Builder()
while making network requests.*/
    fun postRoomInstance(): RoomApiInterface{
        val loggingInterceptor= HttpLoggingInterceptor()

        /*loggingInterceptor is an instance of HttpLoggingInterceptor used to log
       the HTTP requests and responses.*/
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        /*httpClient is an instance of OkHttpClient that configures the HTTP
        client*/
        val httpClient= OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Project-ID", projectId)
                    .addHeader("User-Name", username)
                    .addHeader("User-Secret", password)
                    //.addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()

        //creates a Retrofit instance, specifying the base URL, the HTTP client, and the JSON converter factory to use
        val retrofit= Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RoomApiInterface::class.java)

        return  retrofit!!
    }
}


