package com.napoleon.data.netwok.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.napoleon.domain.WebService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//Url to do petition
val BASE_ROOT = "https://jsonplaceholder.typicode.com/"

//retrofit builder with gsoncerverter and adapter for coroutines
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_ROOT)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

//object to call retrofitservices
object RetrofitService{
    val retrofitService: WebService by lazy {
        retrofit.create(
            WebService::class.java
        )
    }
}