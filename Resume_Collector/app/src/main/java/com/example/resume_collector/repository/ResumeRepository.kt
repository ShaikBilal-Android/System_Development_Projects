package com.example.resume_collector.repository

import android.util.Log
import com.example.resume_collector.data.Resume
import com.example.resume_collector.data.ResumeDao
import com.example.resume_collector.networking.RetrofitInstance
import retrofit2.Response

class ResumeRepository(private val resumeDao: ResumeDao) {
    suspend fun insertResume(resume: Resume) = resumeDao.insertResume(resume)
    suspend fun updateResume(resume: Resume) = resumeDao.updateResume(resume)
    suspend fun deleteResume(resume: Resume) = resumeDao.deleteResume(resume)
//    suspend fun getAllResumes() = resumeDao.getAllResumes()
//    suspend fun getAllResumes(): List<Resume>{return resumeDao.getAllResumes()}
//        suspend fun getAllResumes(): List<Resume>? {
//    try {
//        val response = RetrofitInstance.api.getAllResumes()
//        if (response.isSuccessful) {
//            val resumes = response.body()
//            Log.d("Retrofit", "Success: $resumes")
//        } else {
//            Log.e("Retrofit", "Error: ${response.errorBody()?.string()}")
//        }
//    } catch (e: Exception) {
//        Log.e("Retrofit", "Exception: ${e.message}")
//    }
//}

    suspend fun getAllResumes(): List<Resume>? = try {
        val response = RetrofitInstance.api.getAllResumes()
        if (response.isSuccessful) {
            response.body() // Return the resumes list
        } else {
            Log.e("Retrofit", "Error: ${response.errorBody()?.string()}")
            null
        }
    } catch (e: Exception) {
        Log.e("Retrofit", "Exception: ${e.message}")
        null
    }


}
