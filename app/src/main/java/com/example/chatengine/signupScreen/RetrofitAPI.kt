package com.example.chatengine.signupScreen


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitAPI {

    @POST("users/")
    fun postData(@Body dataModel: DataModel?): Call<DataModel?>?


    companion object{
        var retrofitAPI: RetrofitAPI?=null
        fun postInstance(): RetrofitAPI {
            if(retrofitAPI ==null){
                val url = "https://api.chatengine.io/"
                val privateKey = "69404143-6135-47d6-a1c8-393f5efe2f51"
                val okHttpClient = getOkHttpClient(privateKey)
                retrofitAPI = Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RetrofitAPI::class.java)
            }

            return retrofitAPI!!
        }

        private fun getOkHttpClient(privateKey: String): OkHttpClient {
            // Define your header key for the private key
            val headerKey = "PRIVATE-KEY"
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            // Create your OkHttpClient with an interceptor to add your private key header
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader(headerKey, privateKey)
                        //.addHeader("Accept", "application/json")
                        .build()
                    chain.proceed(request)
                }
                .build()
        }
    }
}

