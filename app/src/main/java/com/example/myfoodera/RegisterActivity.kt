package com.example.myfoodera

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister: Button = findViewById(R.id.btnRegister)
        val tvLogin: TextView = findViewById(R.id.tvLogin)
        val etName: EditText = findViewById(R.id.etName)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val etConfirmPassword: EditText = findViewById(R.id.etConfirmPassword)

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            // Validation
            when {
                name.isEmpty() -> {
                    showError(etName, "Please enter your name")
                }
                email.isEmpty() -> {
                    showError(etEmail, "Please enter email")
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    showError(etEmail, "Please enter valid email")
                }
                password.isEmpty() -> {
                    showError(etPassword, "Please enter password")
                }
                password.length < 4 -> {
                    showError(etPassword, "Password must be at least 6 characters")
                }
                confirmPassword.isEmpty() -> {
                    showError(etConfirmPassword, "Please confirm password")
                }
                password != confirmPassword -> {
                    showError(etConfirmPassword, "Passwords don't match")
                }
                else -> {
                    // Registration successful
                    registerUser(name, email)
                }
            }
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun registerUser(name: String, email: String) {
        // Show success message
        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()

        // Save user data to SharedPreferences (local storage)
        saveUserLocally(name, email)

        // Navigate to HomeActivity
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("USER_NAME", name)
            putExtra("USER_EMAIL", email)
        }
        startActivity(intent)
        finish()
    }

    private fun saveUserLocally(name: String, email: String) {
        val sharedPref = getSharedPreferences("MyFoodEra", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("user_name", name)
            putString("user_email", email)
            putBoolean("is_logged_in", true)
            apply()
        }
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        // Go back to MainActivity when back button is pressed
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}