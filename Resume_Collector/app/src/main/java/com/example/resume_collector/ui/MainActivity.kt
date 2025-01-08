package com.example.resume_collector.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.resume_collector.R
import com.example.resume_collector.data.Resume
import com.example.resume_collector.data.ResumeDatabase
import com.example.resume_collector.networking.RetrofitInstance.api
import com.example.resume_collector.repository.ResumeRepository
import com.example.resume_collector.utils.CameraHelper
import com.example.resume_collector.viewmodel.ResumeViewModel
import com.example.resume_collector.viewmodel.ResumeViewModelFactory
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

//import com.example.resume_collector.viewmodel.ResumeViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var cameraHelper : CameraHelper
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

    private val captureImageLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                // Display the captured image in ImageView
                ivProfilePicture.setImageURI(profilePictureUri)
            } else {
                Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
            }
        }

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

        cameraHelper = CameraHelper(this,captureImageLauncher)

        // setting up the image upload or capture button
        findViewById<Button>(R.id.btnSelectImage).setOnClickListener {
            val  uri = cameraHelper.checkAndLaunchCamera()
            if (uri != null){
                profilePictureUri = uri // save the uri for later use
            }
        }

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

            // Convert the URI to a file
            val file = File(profilePictureUri?.path ?: "")

            // Create the requestBody for the image and resume data
            val requestBodyImage = RequestBody.create(MediaType.parse("image/*"), file)
            val imagePart = MultipartBody.Part.createFormData("image", file.name, requestBodyImage)

            val resumeJson = JSONObject().apply {
                put("fullName", resume.fullName)
                put("email", resume.email)
                put("phone", resume.phone)
                put("address",resume.address)
                put("education",resume.education)
                put("workExperience",resume.workExperience)
                put("skills",resume.skills)
            }

            val requestBodyResume = RequestBody.create(MediaType.parse("application/json"), resumeJson.toString())
            val resumePart = MultipartBody.Part.createFormData("resume", "resume", requestBodyResume)

            // Make the API call to upload both resume and image
            lifecycleScope.launch {
                try {
                    val response = api.uploadResumeWithImage(resumePart, imagePart)
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Resume and Image uploaded successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Failed to upload resume and image", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, "Error uploading resume and image", Toast.LENGTH_SHORT).show()
                    Log.e("Upload Data :->","Resume Upload Error : ${e.message}")
                }
            }

            // Save data to Room
            viewModel.insertResume(resume)
            Toast.makeText(this, "Resume saved!", Toast.LENGTH_SHORT).show()
//            finish() // Go back after saving

            // Navigate to Resume List activity
            val intent = Intent(this, Resume_List::class.java)
            startActivity(intent)
        }

    }

    fun loadImage(profilePictureUri: String, imageView: ImageView) {
        val uri = Uri.parse(profilePictureUri)
        imageView.setImageURI(uri)
    }


}