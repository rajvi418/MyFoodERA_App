package com.example.myfoodera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val menuList: MutableList<MenuItem>, private val context: android.content.Context) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.menuItemImage)
        val itemName: TextView = itemView.findViewById(R.id.menuItemName)
        val itemPrice: TextView = itemView.findViewById(R.id.menuItemPrice)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.menuFavoriteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = menuList[position]

        // Check if item is favorite
        currentItem.isFavorite = FavoriteManager.isFavorite(context, currentItem.id)

        // Set image and text
        holder.itemImage.setImageResource(currentItem.imageResource)
        holder.itemName.text = currentItem.name
        holder.itemPrice.text = currentItem.price

        // Update favorite button icon
        updateFavoriteButton(holder.favoriteButton, currentItem.isFavorite)

        // Favorite button click listener
        holder.favoriteButton.setOnClickListener {
            if (currentItem.isFavorite) {
                // Remove from favorites
                FavoriteManager.removeFavorite(context, currentItem.id)
                currentItem.isFavorite = false
                Toast.makeText(context, "${currentItem.name} removed from favorites", Toast.LENGTH_SHORT).show()
            } else {
                // Add to favorites
                FavoriteManager.addFavorite(context, currentItem)
                currentItem.isFavorite = true
                Toast.makeText(context, "${currentItem.name} added to favorites", Toast.LENGTH_SHORT).show()
            }

            // Update button icon
            updateFavoriteButton(holder.favoriteButton, currentItem.isFavorite)
        }

        // Item click (optional)
        holder.itemView.setOnClickListener {
            // You can add item click functionality here if needed
        }
    }

    private fun updateFavoriteButton(button: ImageButton, isFavorite: Boolean) {
        if (isFavorite) {
            button.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            button.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    override fun getItemCount(): Int = menuList.size
}