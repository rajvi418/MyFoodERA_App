package com.example.myfoodera

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.favoriteRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get favorites from FavoriteManager
        val favoriteList = FavoriteManager.getFavorites(this).toMutableList()

        if (favoriteList.isEmpty()) {
            Toast.makeText(this, "No favorite items yet. Add some from the menu!", Toast.LENGTH_LONG).show()
        }

        // Set adapter
        recyclerView.adapter = MenuAdapter(favoriteList, this)

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
                    // Already in FavoriteActivity
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }

        // Select favorites item by default
        bottomNav.selectedItemId = R.id.nav_favorites
    }

    override fun onResume() {
        super.onResume()
        // Refresh the list when returning to this activity
        val recyclerView = findViewById<RecyclerView>(R.id.favoriteRecycler)
        val favoriteList = FavoriteManager.getFavorites(this).toMutableList()
        recyclerView.adapter = MenuAdapter(favoriteList, this)
    }
}