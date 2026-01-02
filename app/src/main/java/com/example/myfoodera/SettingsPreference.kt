package com.example.myfoodera

import android.content.Context
import android.content.SharedPreferences

class SettingsPreference(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    fun saveSettings(
        notifications: Boolean = true,
        darkMode: Boolean = false,
        sound: Boolean = true
    ) {
        with(sharedPreferences.edit()) {
            putBoolean("notifications", notifications)
            putBoolean("dark_mode", darkMode)
            putBoolean("sound", sound)
            apply()
        }
    }

    fun getNotificationSetting(): Boolean {
        return sharedPreferences.getBoolean("notifications", true)
    }

    fun getDarkModeSetting(): Boolean {
        return sharedPreferences.getBoolean("dark_mode", false)
    }

    fun getSoundSetting(): Boolean {
        return sharedPreferences.getBoolean("sound", true)
    }

    fun resetToDefault() {
        saveSettings(true, false, true)
    }
}   