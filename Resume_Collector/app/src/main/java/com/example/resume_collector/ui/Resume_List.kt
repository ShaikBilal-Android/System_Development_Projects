package com.example.resume_collector.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.resume_collector.R
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resume_collector.data.ResumeDatabase
import com.example.resume_collector.repository.ResumeRepository
import com.example.resume_collector.viewmodel.ResumeViewModel
import com.example.resume_collector.viewmodel.ResumeViewModelFactory

class Resume_List : AppCompatActivity() {

    private lateinit var viewModel: ResumeViewModel
    private lateinit var adapter: ResumeAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resume_list)

        // Initialize the ViewModel
        val dao = ResumeDatabase.getDatabase(applicationContext).resumeDao()
        val repository = ResumeRepository(dao)
        val factory = ResumeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ResumeViewModel::class.java]

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.rvResumeList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ResumeAdapter()
        recyclerView.adapter = adapter

        // Observe data from ViewModel and update RecyclerView
//        viewModel.allResumes.observe(this) { resumes ->
//            adapter.submitList(resumes)
//        }

        viewModel.getAllResumes { resumes ->
            adapter.submitList(resumes)
        }

    }
}
