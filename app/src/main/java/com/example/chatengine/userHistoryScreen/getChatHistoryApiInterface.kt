package com.example.chatengine.userHistoryScreen


import com.example.chatengine.constants.constants.baseUrl
import com.example.chatengine.constants.constants.projectId
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


/*the code defines an API interface and a class that use Retrofit
to make network requests to a server for getting chat user data with a username and password.*/
interface GetChatHistoryApiInterface {

    //gets the data with the specified end point
    @GET("/chats")
    fun getChats(): Call<List<GetChatsDataClass>?>?
}

class GetMyChatsClass(var username: String, var password: String){

    /*getInstance() is a method that returns an instance interface using the Retrofit.Builder()
    while making network requests.*/
    fun getMsgInstance(): GetChatHistoryApiInterface {

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
                    .addHeader("Project-ID", projectId)
                    .addHeader("User-name", username)
                    .addHeader("User-Secret", password)
                    //.addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()

        //creates a Retrofit instance, specifying the base URL, the HTTP client, and the JSON converter factory to use
        val retrofit=Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create( GetChatHistoryApiInterface::class.java)

        return  retrofit!!
    }
}

