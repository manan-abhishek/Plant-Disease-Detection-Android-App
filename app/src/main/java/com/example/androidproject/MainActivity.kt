package com.example.androidproject
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import android.os.Looper
import android.os.Handler
import android.content.Intent
import kotlin.jvm.java


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val sessionManager = SessionManager(this)
        
        Handler(Looper.getMainLooper()).postDelayed({
            // Check if user is already logged in
            if (sessionManager.isLoggedIn()) {
                // User is logged in, go directly to HomeP
                val intent = Intent(this, HomeP::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                // User is not logged in, go to GetStart
                startActivity(Intent(this, GetStart::class.java))
            }
            finish()
        }, 3000)

    }
}