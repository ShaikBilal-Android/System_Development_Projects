package com.example.resume_collector.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resumes")
data class Resume(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val email: String,
    val phone: String,
    val address: String,
    val education: String,
    val workExperience: String,
    val skills: String,
    val profilePictureUri: String
)
