package com.kerim.neptisgame01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kerim.neptisgame01.network.AuthService
import com.kerim.neptisgame01.network.ApiClient
import com.kerim.neptisgame01.models.ApiResponse
import com.kerim.neptisgame01.models.AuthResponse
import com.kerim.neptisgame01.models.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authService = ApiClient.createService(AuthService::class.java)

        val usernameField = findViewById<EditText>(R.id.username)
        val passwordField = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)
        val registerButton = findViewById<Button>(R.id.register_button)

        loginButton.setOnClickListener {
            val username = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username and Password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginRequest = LoginRequest(username, password)

            // Perform login
            authService.login(loginRequest).enqueue(object : Callback<ApiResponse<AuthResponse>> {
                override fun onResponse(call: Call<ApiResponse<AuthResponse>>, response: Response<ApiResponse<AuthResponse>>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        if (apiResponse != null && apiResponse.success) {
                            // Navigate to Home Activity
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Handle unsuccessful login (e.g., invalid credentials)
                            Toast.makeText(this@LoginActivity, "Login failed: ${apiResponse?.message ?: "Unknown error"}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle API error (e.g., 4xx, 5xx responses)
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(this@LoginActivity, "Login failed: ${response.code()} - ${errorBody ?: response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<AuthResponse>>, t: Throwable) {
                    // Handle network error
                    Toast.makeText(this@LoginActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
