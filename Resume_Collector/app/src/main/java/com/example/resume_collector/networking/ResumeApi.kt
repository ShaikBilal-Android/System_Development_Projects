package com.example.resume_collector.networking

import com.example.resume_collector.data.Resume
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response

interface ResumeApi {
    @GET("resumes")
//    suspend fun getAllResumes(): List<Resume>
    suspend fun getAllResumes(): Response<List<Resume>>

    @GET("resumes/{id}")
    suspend fun getResumeById(@Path("id") id: Int): Resume
}
