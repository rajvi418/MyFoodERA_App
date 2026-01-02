package com.example.myfoodera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class DishAdapter(private var dishList: List<Dish>, private val context: android.content.Context) :
    RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishImage: ImageView = itemView.findViewById(R.id.dishImage)
        val dishName: TextView = itemView.findViewById(R.id.dishName)
        val dishPrice: TextView = itemView.findViewById(R.id.dishPrice)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dish, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val currentDish = dishList[position]

        // Check if dish is favorite
        currentDish.isFavorite = FavoriteManager.isFavorite(context, currentDish.id)

        // Set image
        holder.dishImage.setImageResource(currentDish.imageResource)
        holder.dishName.text = currentDish.name
        holder.dishPrice.text = currentDish.price

        // Update favorite button
        updateFavoriteButton(holder.favoriteButton, currentDish.isFavorite)

        // Favorite button click
        holder.favoriteButton.setOnClickListener {
            if (currentDish.isFavorite) {
                // Remove from favorites
                FavoriteManager.removeFavorite(context, currentDish.id)
                currentDish.isFavorite = false
                Toast.makeText(context, "${currentDish.name} removed from favorites", Toast.LENGTH_SHORT).show()
            } else {
                // Add to favorites - convert Dish to MenuItem
                val menuItem = MenuItem(
                    id = currentDish.id,
                    name = currentDish.name,
                    price = currentDish.price,
                    imageResource = currentDish.imageResource,
                    isFavorite = true
                )
                FavoriteManager.addFavorite(context, menuItem)
                currentDish.isFavorite = true
                Toast.makeText(context, "${currentDish.name} added to favorites", Toast.LENGTH_SHORT).show()
            }

            updateFavoriteButton(holder.favoriteButton, currentDish.isFavorite)
        }

        // Dish click
        holder.itemView.setOnClickListener {
        }
    }

    private fun updateFavoriteButton(button: ImageButton, isFavorite: Boolean) {
        if (isFavorite) {
            button.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            button.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    fun updateDishes(newDishes: List<Dish>) {
        // Create a new list to trigger RecyclerView update
        val updatedList = newDishes.map { dish ->
            // Update favorite status for each dish
            dish.copy(isFavorite = FavoriteManager.isFavorite(context, dish.id))
        }
        (dishList as? MutableList<Dish>)?.clear()
        (dishList as? MutableList<Dish>)?.addAll(updatedList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dishList.size
}