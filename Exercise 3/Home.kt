package com.example.exercise2

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {
    private lateinit var usernameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        usernameTextView = findViewById(R.id.username)

        // Get the username from the Intent
        val username = intent.getStringExtra("USERNAME")
        usernameTextView.text = username // Set the username to the TextView
    }
}
