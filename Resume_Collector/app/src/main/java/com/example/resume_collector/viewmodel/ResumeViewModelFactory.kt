package com.example.resume_collector.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.resume_collector.repository.ResumeRepository

class ResumeViewModelFactory(private val repository: ResumeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResumeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResumeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}