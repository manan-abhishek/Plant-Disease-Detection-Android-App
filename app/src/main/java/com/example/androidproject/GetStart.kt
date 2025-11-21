package com.example.androidproject


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.jvm.java


class GetStart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val sessionManager = SessionManager(this)
        

        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, HomeP::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }
        
        setContentView(R.layout.activity_get_start)

        val getStart =  findViewById<Button>(R.id.btnGetStarted)

        getStart.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}