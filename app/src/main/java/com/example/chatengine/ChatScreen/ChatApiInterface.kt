package com.example.chatengine.ChatScreen


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST



interface ChatApiInterface {
    @POST("chats/")
    fun postChatRoom(@Body chatDataClass: ChatDataClass?): Call<ChatDataClass?>?
}

class ChatRoom(username:String,password:String){

    var username=username
    var password=password


    fun postRoomInstance(): ChatApiInterface{
        val loggingInterceptor= HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val url = "https://api.chatengine.io/"

        println("//////////////////////////////////////////$username $password")

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
            .build().create(ChatApiInterface::class.java)

        return  retrofit!!
    }
}




//interface ChatApiInterface {
//    @POST("chats/")
//    fun postChatRoom(@Body chatRoomDataModel: ChatDataClass?): Call<ChatDataClass?>?
//}
//
//class ChatRoomClass(username:String,password:String){
//
//    var username=username
//    var password=password
//
//
//    fun postInstance():  ChatApiInterface {
//        val loggingInterceptor= HttpLoggingInterceptor()
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        val url = "https://api.chatengine.io/"
//
//        val httpClient= OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                    .addHeader("Project-ID", "0ddfa87c-cd5d-4103-8946-0b0ccc96cf9e")
//                    .addHeader("User-Name", username)
//                    .addHeader("User-Secret", password)
//                    //.addHeader("Accept", "application/json")
//                    .build()
//                chain.proceed(request)
//            }
//            .build()
//
//        val retrofit= Retrofit.Builder()
//            .baseUrl(url)
//            .client(httpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build().create( ChatApiInterface::class.java)
//
//        return  retrofit!!
//    }
//}