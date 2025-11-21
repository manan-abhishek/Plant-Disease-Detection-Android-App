package com.example.androidproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class loginPage : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var signupText: TextView
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        

        sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, HomeP::class.java)
            startActivity(intent)
            finish()
            return
        }
        
        setContentView(R.layout.activity_login_page)

        emailInput = findViewById(R.id.edtEmail)
        passwordInput = findViewById(R.id.edtPassword)
        loginButton = findViewById(R.id.btnLogin)
        signupText = findViewById(R.id.txtSignup)

        loginButton.setOnClickListener {
			val email = emailInput.text.toString().trim().lowercase()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please enter email and password")
                return@setOnClickListener
            }
			if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
				showToast("Please enter a valid email")
				return@setOnClickListener
			}

            val request = LoginRequest(email, password)

			loginButton.isEnabled = false
            RetrofitClient.instance.loginUser(request).enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onResponse(call: retrofit2.Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
					loginButton.isEnabled = true
					
					if (response.isSuccessful && response.body() != null) {
						val body = response.body()!!

						if (body.success == true || 
							body.status?.equals("success", ignoreCase = true) == true ||
							body.token?.isNotBlank() == true) {
							

							val email = emailInput.text.toString().trim().lowercase()
							sessionManager.saveLoginSession(body.token, email)
							
							showToast("Login Successful")
							val intent = Intent(this@loginPage, HomeP::class.java)
							intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
							startActivity(intent)
							finish()
						} else {
							
							val errorMsg = body.message.ifEmpty { "Invalid email or password" }
							showToast(errorMsg)
						}
					} else {

						val errorBodyString = try {
							response.errorBody()?.string()
						} catch (e: Exception) {
							null
						}
						
						val errorMessage = if (!errorBodyString.isNullOrBlank()) {
							try {

								val errorResponse = Gson().fromJson(errorBodyString, LoginResponse::class.java)
								errorResponse.message.ifEmpty { "Invalid email or password" }
							} catch (e: Exception) {

								"Invalid email or password"
							}
						} else {
							"Invalid email or password"
						}
						
						showToast(errorMessage)
					}
                }

                @SuppressLint("SuspiciousIndentation")
                override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
					loginButton.isEnabled = true
                    showToast("Login failed: ${t.message}")
                }
            })
        }


        signupText.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
