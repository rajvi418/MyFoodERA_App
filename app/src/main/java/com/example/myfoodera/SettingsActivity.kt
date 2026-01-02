package com.example.myfoodera

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {

    private lateinit var notificationsSwitch: androidx.appcompat.widget.SwitchCompat
    private lateinit var soundSwitch: androidx.appcompat.widget.SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize switches
        notificationsSwitch = findViewById(R.id.notificationsSwitch)
        soundSwitch = findViewById(R.id.soundSwitch)

        // Load saved preferences
        loadSavedSettings()

        // Setup click listeners for information items
        setupClickListeners()

        // Save button
        findViewById<com.google.android.material.button.MaterialButton>(R.id.saveButton).setOnClickListener {
            saveSettings()
            showMessage("Settings saved successfully")
        }

        // Reset button
        findViewById<com.google.android.material.button.MaterialButton>(R.id.resetButton).setOnClickListener {
            showResetConfirmationDialog()
        }

        // Setup Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_menu -> {
                    startActivity(Intent(this, MenuActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_settings -> {
                    // Already in settings
                    true
                }
                else -> false
            }
        }

        bottomNav.selectedItemId = R.id.nav_settings
    }

    private fun loadSavedSettings() {
        val sharedPref = getSharedPreferences("MyFooderaPrefs", MODE_PRIVATE)

        // Load notification setting (default: ON)
        val notificationsEnabled = sharedPref.getBoolean("notifications", true)
        notificationsSwitch.isChecked = notificationsEnabled

        // Load sound setting (default: ON)
        val soundEnabled = sharedPref.getBoolean("sound", true)
        soundSwitch.isChecked = soundEnabled
    }

    private fun saveSettings() {
        val sharedPref = getSharedPreferences("MyFooderaPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Save notification setting
        editor.putBoolean("notifications", notificationsSwitch.isChecked)

        // Save sound setting
        editor.putBoolean("sound", soundSwitch.isChecked)

        editor.apply()
    }

    private fun setupClickListeners() {
        // About App
        findViewById<android.widget.LinearLayout>(R.id.aboutAppLayout).setOnClickListener {
            showAboutAppDialog()
        }

        // Privacy Policy
        findViewById<android.widget.LinearLayout>(R.id.privacyPolicyLayout).setOnClickListener {
            showPrivacyPolicyDialog()
        }

        // Terms and Conditions
        findViewById<android.widget.LinearLayout>(R.id.termsLayout).setOnClickListener {
            showTermsDialog()
        }
    }

    private fun showAboutAppDialog() {
        AlertDialog.Builder(this)
            .setTitle("About MyFoodera")
            .setMessage("MyFoodera v1.0.0\n\n" +
                    "Your favorite food ordering app with a variety of dishes to choose from.\n\n" +
                    "Features:\n" +
                    "• Browse delicious menu items\n" +
                    "• Save favorite dishes\n" +
                    "• Easy navigation\n" +
                    "• Customizable settings")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showPrivacyPolicyDialog() {
        AlertDialog.Builder(this)
            .setTitle("Privacy Policy")
            .setMessage("MyFoodera Privacy Policy\n\n" +
                    "We respect your privacy. This app:\n\n" +
                    "1. Does not collect personal information\n" +
                    "2. Stores app preferences locally on your device\n" +
                    "3. Does not share data with third parties\n" +
                    "4. Uses minimal permissions required for functionality\n\n" +
                    "For questions, contact: support@myfoodera.com")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showTermsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Terms and Conditions")
            .setMessage("MyFoodera Terms and Conditions\n\n" +
                    "By using this app, you agree to:\n\n" +
                    "1. Use the app for personal purposes only\n" +
                    "2. Not misuse the app or its features\n" +
                    "3. Accept that menu items and prices may change\n" +
                    "4. Understand that this is a demo application\n\n" +
                    "Last updated: ${getCurrentDate()}")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showResetConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Reset Settings")
            .setMessage("Are you sure you want to reset all settings to default?")
            .setPositiveButton("Reset") { _, _ ->
                resetToDefault()
                showMessage("Settings reset to default")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun resetToDefault() {
        // Reset switches to default values
        notificationsSwitch.isChecked = true
        soundSwitch.isChecked = true

        // Save the default values
        saveSettings()
    }

    private fun showMessage(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    private fun getCurrentDate(): String {
        val dateFormat = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
        return dateFormat.format(java.util.Date())
    }
}