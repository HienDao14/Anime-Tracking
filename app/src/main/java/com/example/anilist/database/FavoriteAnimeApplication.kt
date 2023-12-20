package com.example.anilist.database

import android.app.Application

class FavoriteAnimeApplication: Application() {
    val database: FavoriteAnimeDatabase by lazy {
        FavoriteAnimeDatabase.getDatabase(this)
    }
}