package com.example.myfoodera

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize views
        val userName = findViewById<TextView>(R.id.userName)
        val userEmail = findViewById<TextView>(R.id.userEmail)
        val userPhone = findViewById<TextView>(R.id.userPhone)
        val userAddress = findViewById<TextView>(R.id.userAddress)
        val userOrders = findViewById<TextView>(R.id.userOrders)
        val favCount = findViewById<TextView>(R.id.favCount)
        val totalSpent = findViewById<TextView>(R.id.totalSpent)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        // TODO: Load actual user data from SharedPreferences or Database
        // For now, setting dummy data
        userName.text = "John Doe"
        userEmail.text = "john.doe@example.com"
        userPhone.text = "+91 9876543210"
        userAddress.text = "123 Main Street, Bangalore, Karnataka - 560001"
        userOrders.text = "47 orders"
        favCount.text = "12" // Get from favorite dishes count
        totalSpent.text = "₹8,450"

        // Logout Button
        logoutButton.setOnClickListener {
            // TODO: Clear user session data
            // Navigate back to MainActivity (Login)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
        }

        // Setup Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_menu -> {
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_favorites -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

        // Select no item by default (Profile is not in bottom nav)
    }
}