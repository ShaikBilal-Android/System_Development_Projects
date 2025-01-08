package com.example.resume_collector.networking

import com.example.resume_collector.data.Resume
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ResumeApi {
    @GET("resumes")
//    suspend fun getAllResumes(): List<Resume>
    suspend fun getAllResumes(): Response<List<Resume>>

    @GET("resumes/{id}")
    suspend fun getResumeById(@Path("id") id: Int): Resume

    @Multipart
    @POST("resumes/upload")
    suspend fun uploadResumeWithImage(
        @Part resume: MultipartBody.Part,
        @Part image: MultipartBody.Part
    ): Response<Resume>
}
