package com.example.resume_collector.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import java.io.File

class CameraHelper(
    private val context: Context,
    private val activityResultLauncher: ActivityResultLauncher<Uri>
) {
    private lateinit var imageUri: Uri

    fun checkAndLaunchCamera(): Uri? {
        val imageFile = createImageFile()
        return if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                imageFile
            )
            activityResultLauncher.launch(imageUri)
            imageUri
        } else {
            Toast.makeText(context, "Failed to create file", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun createImageFile(): File? {
        return try {
            val imageFileName = "captured_image_${System.currentTimeMillis()}.jpg"
            val storageDir = (context as Activity).getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            File(storageDir, imageFileName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
