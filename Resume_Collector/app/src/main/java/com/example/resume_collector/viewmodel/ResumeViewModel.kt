package com.example.resume_collector.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.resume_collector.data.Resume
import com.example.resume_collector.repository.ResumeRepository
import kotlinx.coroutines.launch

class ResumeViewModel(private val repository: ResumeRepository) : ViewModel() {

    fun insertResume(resume: Resume) {
        try {
            viewModelScope.launch {
                repository.insertResume(resume)
                Log.e("Data Insertion : ","Resume saved successfully!")
            }
        }catch (e:Exception){
            e.printStackTrace()
            Log.e("Data Insertion Failed","Error saving data!",e)
        }
    }

    fun updateResume(resume: Resume) {
        viewModelScope.launch {
            repository.updateResume(resume)
        }
    }

    fun deleteResume(resume: Resume) {
        viewModelScope.launch {
            repository.deleteResume(resume)
        }
    }

    fun getAllResumes(callback: (List<Resume>) -> Unit) {
        viewModelScope.launch {
            val resumes = repository.getAllResumes()
            if (resumes != null) {
                callback(resumes)
            }
        }
    }
}

class ResumeViewModelFactory(private val repository: ResumeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResumeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResumeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
