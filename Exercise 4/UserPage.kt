package com.example.exercise2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserPage : AppCompatActivity() {

    private lateinit var pickProfileImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var pickReelsImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var gridImages: GridLayout
    private lateinit var profileImageView: ImageView // Declare it at the class level

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = intent.getStringExtra("USERNAME")
        val usernameTextView: TextView = findViewById(R.id.username)
        usernameTextView.text = username

        profileImageView = findViewById(R.id.profile_picture) // Initialize here
        val editProfileButton: Button = findViewById(R.id.edit_profile_button)

        gridImages = findViewById(R.id.grid_images)
        val reelsImageView: ImageView = findViewById(R.id.reels_icon)

        // Use different launchers for different actions
        pickProfileImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedImageUri: Uri? = result.data?.data
                    selectedImageUri?.let {
                        profileImageView.setImageURI(it) // Update profile picture
                    }
                }
            }

        pickReelsImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedImageUri: Uri? = result.data?.data
                    selectedImageUri?.let {
                        // Add to grid
                        val imageView = ImageView(this).apply {
                            layoutParams = GridLayout.LayoutParams().apply {
                                width = 0
                                height = 300
                                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                                setMargins(2, 2, 2, 2)
                            }
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageURI(it)
                        }
                        gridImages.addView(imageView)
                    }
                }
            }

        editProfileButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            pickProfileImageLauncher.launch(intent) // Use the profile image launcher
        }

        reelsImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            pickReelsImageLauncher.launch(intent) // Use the reels image launcher
        }
    }
}