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

class SignupActivity: AppCompatActivity() {
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sessionManager = SessionManager(this)
        
        // Check if user is already logged in
        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, HomeP::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }
        
        setContentView(R.layout.activity_signup)

        firstName = findViewById(R.id.edtFirstName)
        lastName = findViewById(R.id.edtLastName)
        email = findViewById(R.id.edtEmail)
        password = findViewById(R.id.edtPassword)
        registerBtn = findViewById(R.id.btnSignup)

        val loginText: TextView = findViewById(R.id.txtLogin)

        registerBtn.setOnClickListener {
            val f = firstName.text.toString().trim()
            val l = lastName.text.toString().trim()
			val e = email.text.toString().trim().lowercase()
			val p = password.text.toString().trim()

			if (f.isEmpty() || l.isEmpty() || e.isEmpty() || p.isEmpty() ) {
                showToast("Please fill all fields")
			} else if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
				showToast("Please enter a valid email")
			} else if (p.length < 8) {
				showToast("Password must be at least 8 characters")
            } else {
                val request = RegisterRequest(f, l, e, p)

				registerBtn.isEnabled = false
				RetrofitClient.instance.registerUser(request)
                    .enqueue(object : retrofit2.Callback<RegisterResponse> {
                        override fun onResponse(
                            call: retrofit2.Call<RegisterResponse>,
                            response: retrofit2.Response<RegisterResponse>
                        ) {
							registerBtn.isEnabled = true
							if (response.isSuccessful && response.body() != null) {
                                val body = response.body()!!
                                if (body.success == true) {
                                    showToast("Signup Successful! Please login.")
                                    startActivity(Intent(this@SignupActivity, loginPage::class.java))
                                    finish()
                                } else {
                                    showToast(body.message.ifEmpty { "Registration failed. Please try again." })
                                }
                            } else {
								// Try to parse error body as RegisterResponse first
								val errorBodyString = try {
									response.errorBody()?.string()
								} catch (e: Exception) {
									null
								}
								
								val errorMessage = if (!errorBodyString.isNullOrBlank()) {
									try {
										val errorResponse = Gson().fromJson(errorBodyString, RegisterResponse::class.java)
										errorResponse.message.ifEmpty { "Registration failed" }
									} catch (e: Exception) {
										errorBodyString.take(100) // Limit length
									}
								} else {
									response.message().ifEmpty { "Server error: ${response.code()}" }
								}
								
								showToast(errorMessage)
                            }
                        }


                        @SuppressLint("SuspiciousIndentation")
                        override fun onFailure(call: retrofit2.Call<RegisterResponse>, t: Throwable) {
							registerBtn.isEnabled = true
                            showToast("Error: ${t.message}")
                        }
                    })
            }
        }



        loginText.setOnClickListener {
            val intent = Intent(this, loginPage::class.java)
            startActivity(intent)
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
