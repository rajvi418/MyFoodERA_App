package com.example.myfoodera

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.menuRecycler)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Create menu list with 30 items
        val menuList = mutableListOf<MenuItem>()

        // Add 30 menu items with different images
        menuList.add(MenuItem(1, "Masala Dosa", "₹129", R.drawable.dosa1))
        menuList.add(MenuItem(2, "Cheese Pizza", "₹199", R.drawable.pizza))
        menuList.add(MenuItem(3, "Paneer Pizza", "₹179", R.drawable.pizza2))
        menuList.add(MenuItem(4, "Margherita", "₹159", R.drawable.pizza3))
        menuList.add(MenuItem(5, "Pepperoni", "₹219", R.drawable.pizza4))
        menuList.add(MenuItem(6, "Veg Supreme", "₹189", R.drawable.pizza5))
        menuList.add(MenuItem(7, "Chicken Pizza", "₹239", R.drawable.pizza6))
        menuList.add(MenuItem(8, "Plain Dosa", "₹79", R.drawable.dosa))
        menuList.add(MenuItem(9, "Rava Dosa", "₹149", R.drawable.dosa2))
        menuList.add(MenuItem(10, "Onion Dosa", "₹99", R.drawable.dosa3))
        menuList.add(MenuItem(11, "Paper Dosa", "₹119", R.drawable.dosa4))
        menuList.add(MenuItem(12, "Ghee Dosa", "₹139", R.drawable.dosa5))
        menuList.add(MenuItem(13, "Fresh Salad", "₹149", R.drawable.salad))
        menuList.add(MenuItem(14, "Greek Salad", "₹179", R.drawable.salad1))
        menuList.add(MenuItem(15, "Caesar Salad", "₹169", R.drawable.salad2))
        menuList.add(MenuItem(16, "Fruit Salad", "₹139", R.drawable.salad3))
        menuList.add(MenuItem(17, "Pasta Salad", "₹159", R.drawable.salad4))
        menuList.add(MenuItem(18, "Chicken Salad", "₹199", R.drawable.salad5))
        menuList.add(MenuItem(19, "Garden Salad", "₹129", R.drawable.salad6))
        menuList.add(MenuItem(20, "Vegetable Rice", "₹149", R.drawable.rice))
        menuList.add(MenuItem(21, "Chicken Rice Bowl", "₹199", R.drawable.ricebowl1))
        menuList.add(MenuItem(22, "Veg Rice Bowl", "₹169", R.drawable.ricebowl2))
        menuList.add(MenuItem(23, "Egg Fried Rice", "₹179", R.drawable.ricebowl3))
        menuList.add(MenuItem(24, "Special Rice Bowl", "₹219", R.drawable.ricebowl4))
        menuList.add(MenuItem(25, "Seafood Rice", "₹249", R.drawable.ricebowl5))
        menuList.add(MenuItem(26, "Premium Rice Bowl", "₹279", R.drawable.ricebowl6))
        menuList.add(MenuItem(27, "Fuli Hah Special", "₹299", R.drawable.fullthali1))
        menuList.add(MenuItem(28, "Fuli Hah Deluxe", "₹349", R.drawable.fullthali2))
        menuList.add(MenuItem(29, "Fuli Hah Supreme", "₹399", R.drawable.fullthali3))
        menuList.add(MenuItem(30, "Fuli Hah Royal", "₹449", R.drawable.fullthali5))

        // Set adapter with context
        recyclerView.adapter = MenuAdapter(menuList, this)

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
                    true
                }
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    finish()
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

        bottomNav.selectedItemId = R.id.nav_menu
    }
}