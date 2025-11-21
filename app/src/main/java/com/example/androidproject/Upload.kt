package com.example.androidproject

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Upload : AppCompatActivity() {

    private lateinit var uploadBox: FrameLayout
    private lateinit var uploadText: TextView
    private lateinit var btnGallery: Button
    private lateinit var btnCamera: Button
    private lateinit var analyzeButton: Button
    private lateinit var previewImage: ImageView

    private var imageUri: Uri? = null
    private var cameraImageUri: Uri? = null

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(this, "Camera permission is required to capture photos", Toast.LENGTH_SHORT).show()
        }
    }


    private val galleryPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openGallery()
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                openGallery()
            } else {
                Toast.makeText(this, "Storage permission is required to select images", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private val pickVisualMediaLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            displayImage(it)
            uploadText.text = "Image selected from gallery ✅"
        } ?: run {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }


    private val getContentLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            displayImage(it)
            uploadText.text = "Image selected from gallery ✅"
        } ?: run {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }


    private val intentGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                imageUri = it
                displayImage(it)
                uploadText.text = "Image selected from gallery ✅"
            } ?: run {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Camera launcher
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && cameraImageUri != null) {
            imageUri = cameraImageUri
            displayImage(cameraImageUri!!)
            uploadText.text = "Photo captured successfully ✅"
        } else {
            Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        uploadBox = findViewById(R.id.uploadBox)
        uploadText = findViewById(R.id.uploadText)
        btnGallery = findViewById(R.id.btnGallery)
        btnCamera = findViewById(R.id.btnCamera)
        analyzeButton = findViewById(R.id.analyzeButton)
        previewImage = findViewById(R.id.previewImage)

        // Gallery button click
        btnGallery.setOnClickListener {
            // On Android 13+, GetContent doesn't require permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                openGallery()
            } else if (checkGalleryPermission()) {
                openGallery()
            } else {
                requestGalleryPermission()
            }
        }


        btnCamera.setOnClickListener {
            if (!isCameraAvailable()) {
                Toast.makeText(this, "Camera is not available on this device", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (checkCameraPermission()) {
                openCamera()
            } else {
                requestCameraPermission()
            }
        }

        uploadBox.setOnClickListener {
            // On Android 13+, GetContent doesn't require permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                openGallery()
            } else if (checkGalleryPermission()) {
                openGallery()
            } else {
                requestGalleryPermission()
            }
        }

        analyzeButton.setOnClickListener {
            if (imageUri != null) {
                Toast.makeText(this, "Analyzing image...", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Please select or capture an image first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkGalleryPermission(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return true
        }
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    private fun isCameraAvailable(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    private fun requestCameraPermission() {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun requestGalleryPermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun openGallery() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pickVisualMediaLauncher.launch(
                    androidx.activity.result.PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            } else {
                getContentLauncher.launch("image/*")
            }
        } catch (e: Exception) {
            openGalleryWithIntent()
        }
    }

    private fun openGalleryWithIntent() {
        try {
            // Try ACTION_PICK first (opens system gallery)
            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"
            
            // Also create a chooser to let user pick which app to use
            val chooserIntent = Intent.createChooser(pickIntent, "Select Image")
            
            // Add ACTION_GET_CONTENT as alternative
            val getContentIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(getContentIntent))
            
            intentGalleryLauncher.launch(chooserIntent)
        } catch (e: Exception) {
            Toast.makeText(
                this, 
                "Unable to open gallery. Please ensure you have photos in your device and grant necessary permissions.", 
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun openCamera() {
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show()
            null
        }

        photoFile?.also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "${applicationContext.packageName}.fileprovider",
                it
            )
            cameraImageUri = photoURI
            cameraLauncher.launch(photoURI)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    private fun displayImage(uri: Uri) {
        previewImage.setImageURI(uri)
        previewImage.visibility = ImageView.VISIBLE
    }
}
