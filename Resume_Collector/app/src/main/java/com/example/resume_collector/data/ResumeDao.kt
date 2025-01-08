package com.example.resume_collector.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ResumeDao {
    @Insert
    suspend fun insertResume(resume: Resume)

    @Update
    suspend fun updateResume(resume: Resume)

    @Delete
    suspend fun deleteResume(resume: Resume)

//    @Query("SELECT * FROM resumes ORDER BY id DESC")
//    suspend fun getAllResumes(): List<Resume>

    @Query("SELECT * FROM resumes")
     fun getAllResumesForUri(): LiveData<List<Resume>>
}
