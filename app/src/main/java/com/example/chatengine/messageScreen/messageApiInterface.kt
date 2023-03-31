package com.example.chatengine.messageScreen



import com.example.chatengine.constants.constants.projectId
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


/*the code defines an API interface and a class that use Retrofit
to make network requests to a server for getting and posting user message data with a username and password.*/
interface messageApiInterface {

    //gets the data with the specified end point
    @GET("messages/")
    fun getMsg(): Call<List<RecieveDataClass>?>?

    //sends the data with the specified end point
    @POST("messages/")
    fun postMsg(@Body msgDataClassModel: MsgDataClassModel?): Call<MsgDataClassModel?>?
}

class MessageClass(var username: String, var password: String, val chatId: Int){


    /*getInstance() is a method that returns an instance interface using the Retrofit.Builder()
    while making network requests to send message.*/
    fun postMsgInstance(): messageApiInterface{

        /*loggingInterceptor is an instance of HttpLoggingInterceptor used to log
       the HTTP requests and responses.*/
        val loggingInterceptor= HttpLoggingInterceptor()
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
            .baseUrl("https://api.chatengine.io/chats/${chatId}/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(messageApiInterface::class.java)

        return  retrofit!!
    }


    /*getInstance() is a method that returns an instance interface using the Retrofit.Builder()
    while making network requests to get message.*/
    fun getMsgInstance(): messageApiInterface {

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
            .baseUrl("https://api.chatengine.io/chats/${chatId}/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(messageApiInterface::class.java)

        return  retrofit!!
    }
}

