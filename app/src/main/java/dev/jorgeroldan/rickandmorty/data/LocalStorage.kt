package dev.jorgeroldan.rickandmorty.data

import android.content.SharedPreferences

interface LocalStorage {

    fun isFavourite(characterId: String): Boolean
    fun storeFavourite(characterId: String)
    fun deleteFavourite(characterId: String)
}

class LocalStorageImpl(private val prefs: SharedPreferences) : LocalStorage {

    private val favouriteIds: MutableList<String> = mutableListOf()

    override fun isFavourite(characterId: String): Boolean {
        if (favouriteIds.isEmpty()) {
            val storedFavourites = prefs.getString(FAVOURITES_KEY, "")?.split(SEPARATOR_CHAR) ?: emptyList()
            favouriteIds.addAll(storedFavourites)
            favouriteIds.filter { it.isNotEmpty() }
        }
        return favouriteIds.contains(characterId)
    }

    override fun storeFavourite(characterId: String) {
        if (favouriteIds.contains(characterId) || characterId.isEmpty()) return
        favouriteIds.add(characterId)
        saveListToPrefs()
    }

    override fun deleteFavourite(characterId: String) {
        if (!favouriteIds.contains(characterId) || characterId.isEmpty()) return
        favouriteIds.remove(characterId)
        saveListToPrefs()
    }

    private fun saveListToPrefs() {
        val storeValue = favouriteIds.joinToString(SEPARATOR_CHAR)
        prefs.edit()
            .putString(FAVOURITES_KEY, storeValue)
            .apply()
    }

    companion object {
        private const val FAVOURITES_KEY = "favourites_key"
        private const val SEPARATOR_CHAR = "|"
    }
}
