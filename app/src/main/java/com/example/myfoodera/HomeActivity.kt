package com.example.myfoodera

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout

class HomeActivity : AppCompatActivity() {

    private lateinit var dishAdapter: DishAdapter

    // Different dish lists for categories
    private val pizzaDishes = mutableListOf<Dish>()
    private val dosaDishes = mutableListOf<Dish>()
    private val saladDishes = mutableListOf<Dish>()
    private val fullDishDishes = mutableListOf<Dish>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Setup category tabs
        setupCategoryTabs()

        // Profile button click
        val profileButton = findViewById<MaterialButton>(R.id.profileButton)
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.dishRecycler)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Initialize dish lists for different categories
        initializeDishLists()

        // Show Pizza dishes by default - PASS CONTEXT TO ADAPTER
        dishAdapter = DishAdapter(pizzaDishes, this)
        recyclerView.adapter = dishAdapter

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    true
                }
                R.id.nav_menu -> {
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_favorites -> {
                    // Navigate to FavoriteActivity
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        bottomNav.selectedItemId = R.id.nav_home
    }

    override fun onResume() {
        super.onResume()
        refreshFavoriteStatus()
    }

    private fun refreshFavoriteStatus() {
        pizzaDishes.forEach { dish ->
            dish.isFavorite = FavoriteManager.isFavorite(this, dish.id)
        }
        dosaDishes.forEach { dish ->
            dish.isFavorite = FavoriteManager.isFavorite(this, dish.id)
        }
        saladDishes.forEach { dish ->
            dish.isFavorite = FavoriteManager.isFavorite(this, dish.id)
        }
        fullDishDishes.forEach { dish ->
            dish.isFavorite = FavoriteManager.isFavorite(this, dish.id)
        }

        // Update the adapter
        dishAdapter.notifyDataSetChanged()
    }

    private fun setupCategoryTabs() {
        val tabLayout = findViewById<TabLayout>(R.id.foodTabs)

        // Add tabs
        tabLayout.addTab(tabLayout.newTab().setText("🍕 Pizza"))
        tabLayout.addTab(tabLayout.newTab().setText("🥞 Dosa"))
        tabLayout.addTab(tabLayout.newTab().setText("🥗 Salad"))
        tabLayout.addTab(tabLayout.newTab().setText("🍛 Full Meal"))

        // Tab selection (dishes category)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        pizzaDishes.forEach { dish ->
                            dish.isFavorite = FavoriteManager.isFavorite(this@HomeActivity, dish.id)
                        }
                        dishAdapter.updateDishes(pizzaDishes)
                    }
                    1 -> {
                        dosaDishes.forEach { dish ->
                            dish.isFavorite = FavoriteManager.isFavorite(this@HomeActivity, dish.id)
                        }
                        dishAdapter.updateDishes(dosaDishes)
                    }
                    2 -> {
                        saladDishes.forEach { dish ->
                            dish.isFavorite = FavoriteManager.isFavorite(this@HomeActivity, dish.id)
                        }
                        dishAdapter.updateDishes(saladDishes)
                    }
                    3 -> {
                        fullDishDishes.forEach { dish ->
                            dish.isFavorite = FavoriteManager.isFavorite(this@HomeActivity, dish.id)
                        }
                        dishAdapter.updateDishes(fullDishDishes)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initializeDishLists() {
        // 🍕 Pizza dishes
        pizzaDishes.addAll(listOf(
            Dish(1, "Margherita Pizza", "₹199", R.drawable.pizza),
            Dish(2, "Pepperoni Pizza", "₹249", R.drawable.pizza2),
            Dish(3, "Veggie Pizza", "₹179", R.drawable.pizza3),
            Dish(4, "Cheese Burst", "₹299", R.drawable.pizza4),
            Dish(5, "Farmhouse Pizza", "₹179", R.drawable.pizza5),
            Dish(6, "Double Cheese Burst", "₹299", R.drawable.pizza6)
        ))

        // 🥞 Dosa dishes
        dosaDishes.addAll(listOf(
            Dish(7, "Masala Dosa", "₹99", R.drawable.dosa1),
            Dish(8, "Plain Dosa", "₹79", R.drawable.dosa2),
            Dish(9, "Onion Dosa", "₹119", R.drawable.dosa3),
            Dish(10, "Butter Dosa", "₹139", R.drawable.dosa4),
            Dish(11, "Wined Roll Dosa", "₹119", R.drawable.dosa5),
            Dish(12, "Dhosa Dhosa", "₹139", R.drawable.dosa)
        ))

        // 🥗 Salad dishes
        saladDishes.addAll(listOf(
            Dish(13, "Greek Salad", "₹159", R.drawable.salad1),
            Dish(14, "Caesar Salad", "₹179", R.drawable.salad2),
            Dish(15, "Fruit Salad", "₹129", R.drawable.salad3),
            Dish(16, "Veg Salad", "₹99", R.drawable.salad4),
            Dish(17, "Vegetable Salad", "₹129", R.drawable.salad5),
            Dish(18, "Fruit Vegetable Salad", "₹99", R.drawable.salad6)
        ))

        // 🍛 Full Dish meals
        fullDishDishes.addAll(listOf(
            Dish(19, "Gujarati Thali", "₹299", R.drawable.fulldish),
            Dish(20, "Veg Thali", "₹199", R.drawable.fullthali2),
            Dish(21, "Chinese Meal", "₹349", R.drawable.fullthali3),
            Dish(22, "Paneer Tikka", "₹249", R.drawable.fullthali4),
            Dish(23, "Butter Thali", "₹349", R.drawable.fullthali5),
            Dish(24, "Thailand Thali", "₹249", R.drawable.fullthali6)
        ))
    }
}