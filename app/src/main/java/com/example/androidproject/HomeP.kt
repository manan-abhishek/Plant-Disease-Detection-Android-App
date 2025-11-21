package com.example.androidproject


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar

class HomeP : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize SessionManager
        sessionManager = SessionManager(this)
        
        // Check if user is logged in
        if (!sessionManager.isLoggedIn()) {
            // User is not logged in, redirect to login
            val intent = Intent(this, loginPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }
        
        setContentView(R.layout.activity_home_p)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        val getStarted = findViewById<Button>(R.id.btnGetStarted) ;
        getStarted.setOnClickListener{
            val intent = Intent(this,Upload::class.java) ;
            startActivity(intent)  ;
        }

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> showToast("Home Selected")
                R.id.nav_upload -> {
                    val intent = Intent(this, Upload::class.java) ;
                    startActivity(intent) ;
                }
                R.id.nav_history -> {
                    val intent = Intent(this, HistoryActivity::class.java)  ;
                    startActivity(intent) ;
                }
                R.id.nav_help -> {
                    val intent = Intent(this, Help::class.java)
                    startActivity(intent) ;
                }
                R.id.nav_profile -> {
                    // Logout user
                    sessionManager.logout()
                    showToast("Logged out successfully")
                    val intent = Intent(this, loginPage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
