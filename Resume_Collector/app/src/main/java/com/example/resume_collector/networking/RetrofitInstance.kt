package com.example.resume_collector.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    //    private const val BASE_URL = "http://localhost:5000/" // Replace with your base URL
    //    private const val BASE_URL = "https://apsac.ap.gov.in/" // Replace with your base URL
    private const val BASE_URL = "http://172.16.16.82:3000/" // Replace with your base URL
//    private const val BASE_URL = "https://apsac.ap.gov.in/" // Replace with your base URL


    val api: ResumeApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ResumeApi::class.java)
    }
}