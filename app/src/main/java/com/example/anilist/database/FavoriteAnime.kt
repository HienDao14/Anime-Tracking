package com.example.anilist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_anime")
data class FavoriteAnime(
    @PrimaryKey
    val malId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "image")
    val imageUrl: String,
    @ColumnInfo(name = "score")
    val score: Double,
    @ColumnInfo(name = "isAiring")
    val airing: String
)