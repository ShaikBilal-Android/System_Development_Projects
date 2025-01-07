package com.example.resume_collector.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.resume_collector.R
import com.example.resume_collector.data.Resume
import com.example.resume_collector.data.ResumeDatabase
import com.example.resume_collector.repository.ResumeRepository
import com.example.resume_collector.viewmodel.ResumeViewModel
import com.example.resume_collector.viewmodel.ResumeViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ResumeViewModel
    private lateinit var ivProfilePicture: ImageView
    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etAddress: EditText
    private lateinit var etEducation: EditText
    private lateinit var etWorkExperience: EditText
    private lateinit var etSkills: EditText
    private lateinit var btnSaveResume: Button
    private var profilePictureUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize the ViewModel
        val dao = ResumeDatabase.getDatabase(applicationContext).resumeDao()
        val repository = ResumeRepository(dao)
        val factory = ResumeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ResumeViewModel::class.java]

        // Initialize Views
        ivProfilePicture = findViewById(R.id.ivProfilePicture)
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        etAddress = findViewById(R.id.etAddress)
        etEducation = findViewById(R.id.etEducation)
        etWorkExperience = findViewById(R.id.etWorkExperience)
        etSkills = findViewById(R.id.etSkills)
        btnSaveResume = findViewById(R.id.btnSaveResume)

        // Save Button Click Listener
        btnSaveResume.setOnClickListener {
            val resume = Resume(
                fullName = etFullName.text.toString(),
                email = etEmail.text.toString(),
                phone = etPhone.text.toString(),
                address = etAddress.text.toString(),
                education = etEducation.text.toString(),
                workExperience = etWorkExperience.text.toString(),
                skills = etSkills.text.toString(),
                profilePictureUri = profilePictureUri?.toString() ?: ""
            )
            // Save data to Room
            viewModel.insertResume(resume)
//            finish() // Go back after saving

            // Navigate to Resume List activity
            val intent = Intent(this, Resume_List::class.java)
            startActivity(intent)
        }

    }

}