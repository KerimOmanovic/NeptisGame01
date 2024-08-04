package com.kerim.neptisgame01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kerim.neptisgame01.models.User
import com.kerim.neptisgame01.repositories.AuthRepository

class RegisterActivity : AppCompatActivity() {
    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val firstNameField = findViewById<EditText>(R.id.first_name)
        val lastNameField = findViewById<EditText>(R.id.last_name)
        val ageField = findViewById<EditText>(R.id.age)
        val usernameField = findViewById<EditText>(R.id.username)
        val passwordField = findViewById<EditText>(R.id.password)
        val registerButton = findViewById<Button>(R.id.register_button)

        registerButton.setOnClickListener {
            val firstName = firstNameField.text.toString().trim()
            val lastName = lastNameField.text.toString().trim()
            val age = ageField.text.toString().trim().toIntOrNull() ?: 0
            val username = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (firstName.isEmpty() || lastName.isEmpty() || age <= 0 || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(null, firstName, lastName, age, username, password)

            authRepository.register(user) { registeredUser ->
                if (registeredUser != null) {
                    Log.d("RegisterActivity", "Registration successful: $registeredUser")
                    val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("RegisterActivity", "Registration failed")
                    Toast.makeText(this@RegisterActivity, "Registration failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
