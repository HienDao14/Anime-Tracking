package com.example.anilist.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Upsert
    suspend fun upsertAnime(anime: FavoriteAnime)

    @Delete
    suspend fun deleteAnime(anime: FavoriteAnime)

    @Query("SELECT * FROM favorite_anime ORDER BY title ASC")
    fun getAnimeByTitle() : Flow<List<FavoriteAnime>>

    @Query("SELECT * FROM favorite_anime ORDER BY score ASC")
    fun getAnimeByScore() : Flow<List<FavoriteAnime>>
}