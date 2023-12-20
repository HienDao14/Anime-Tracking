package com.example.anilist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteAnime::class], version = 1, exportSchema = false)
abstract class FavoriteAnimeDatabase : RoomDatabase(){
    abstract fun animeDao(): AnimeDao

    companion object{
        @Volatile
        private var INSTANCE : FavoriteAnimeDatabase?= null
        fun getDatabase(context: Context): FavoriteAnimeDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteAnimeDatabase::class.java,
                    "favorite_anime_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}