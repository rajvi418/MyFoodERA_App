package com.example.myfoodera

import android.content.Context

object FavoriteManager {
    private const val PREF_NAME = "favorites_pref"
    private const val KEY_FAVORITES = "favorite_items"

    fun addFavorite(context: Context, menuItem: MenuItem) {
        val favorites = getFavorites(context).toMutableList()
        if (!favorites.any { it.id == menuItem.id }) {
            favorites.add(menuItem)
            saveFavorites(context, favorites)
        }
    }

    fun removeFavorite(context: Context, menuItemId: Int) {
        val favorites = getFavorites(context).toMutableList()
        favorites.removeAll { it.id == menuItemId }
        saveFavorites(context, favorites)
    }

    fun getFavorites(context: Context): List<MenuItem> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val favoritesJson = prefs.getString(KEY_FAVORITES, "")

        return if (favoritesJson.isNullOrEmpty()) {
            emptyList()
        } else {
            // Simple parsing - you can use Gson later for better handling
            try {
                parseFavorites(favoritesJson)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    fun isFavorite(context: Context, menuItemId: Int): Boolean {
        return getFavorites(context).any { it.id == menuItemId }
    }

    private fun saveFavorites(context: Context, favorites: List<MenuItem>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = favorites.joinToString("|") { "${it.id},${it.name},${it.price},${it.imageResource}" }
        prefs.edit().putString(KEY_FAVORITES, json).apply()
    }

    private fun parseFavorites(json: String): List<MenuItem> {
        if (json.isEmpty()) return emptyList()

        return json.split("|").mapNotNull { itemStr ->
            val parts = itemStr.split(",")
            if (parts.size >= 4) {
                try {
                    MenuItem(
                        id = parts[0].toInt(),
                        name = parts[1],
                        price = parts[2],
                        imageResource = parts[3].toInt(),
                        isFavorite = true
                    )
                } catch (e: Exception) {
                    null
                }
            } else {
                null
            }
        }
    }
}