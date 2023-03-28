package com.example.chatengine.messageScreen



import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface PostMessageAPI {

    @GET("/chats/153494/messages/")
    fun getMsg(): Call<List<RecieveDataClass>?>?
    @POST("chats/153494/messages/")
    fun postMsg(@Body msgDataClassModel: MsgDataClassModel?): Call<MsgDataClassModel?>?
}

class MessageClass(username:String,password:String){

    var username=username
    var password=password


    fun postMsgInstance(): PostMessageAPI{
        val loggingInterceptor= HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val url = "https://api.chatengine.io/"

        val httpClient= OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Project-ID", "0ddfa87c-cd5d-4103-8946-0b0ccc96cf9e")
                    .addHeader("User-Name", username)
                    .addHeader("User-Secret", password)
                    //.addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit= Retrofit.Builder()
            .baseUrl(url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PostMessageAPI::class.java)

        return  retrofit!!
    }


    fun getMsgInstance(): PostMessageAPI {
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
            .build().create(PostMessageAPI::class.java)

        return  retrofit!!
    }
}

