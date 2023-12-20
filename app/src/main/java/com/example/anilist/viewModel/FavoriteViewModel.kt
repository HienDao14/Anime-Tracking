package com.example.anilist.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.anilist.database.AnimeDao
import com.example.anilist.database.FavoriteAnime
import kotlinx.coroutines.launch

class FavoriteViewModel(private val dao: AnimeDao): ViewModel() {

    val allFavoriteAnime : LiveData<List<FavoriteAnime>> = dao.getAnimeByTitle().asLiveData()
   private fun upsertAnime(anime: FavoriteAnime){
       viewModelScope.launch {
           dao.upsertAnime(anime)
       }
   }

    private fun getNewAnime(malId: Int, title: String, imageUrl: String, score: Double, airing: String)
        :FavoriteAnime{
        return FavoriteAnime(malId, title, imageUrl, score, airing)
    }

    fun addNewAnime(malId: Int, title: String, imageUrl: String, score: Double, airing: String){
        val newAnime = getNewAnime(malId, title, imageUrl, score, airing)
        upsertAnime(newAnime)
    }

    fun getAnimeByTitle(){

    }
}

class AnimeViewModelFactory(private val dao: AnimeDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown")
    }
}