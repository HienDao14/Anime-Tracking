package com.example.anilist.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anilist.pojo.Anime
import com.example.anilist.pojo.AnimeFull
import com.example.anilist.pojo.Character
import com.example.anilist.pojo.Manga
import com.example.anilist.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _animeFullLiveData = MutableLiveData<AnimeFull>()
    val animeFullLiveData : LiveData<AnimeFull>
        get() = _animeFullLiveData

    private val _mangaFullLiveData = MutableLiveData<Manga>()
    val mangaFullLiveData : LiveData<Manga>
        get() = _mangaFullLiveData

    fun getAnimeById(id : Int){
        RetrofitInstance.api.getAnimeById(id).enqueue(object : Callback<AnimeFull>{
            override fun onResponse(call: Call<AnimeFull>, response: Response<AnimeFull>) {
                if(response.body() != null){
                    _animeFullLiveData.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<AnimeFull>, t: Throwable) {
                Log.d("DetailViewModel", "Error" + t.message.toString())
            }
        })
    }

    fun getMangaById(id: Int){
            RetrofitInstance.api.getMangaById(id).enqueue(object : Callback<Manga>{
                override fun onResponse(call: Call<Manga>, response: Response<Manga>) {
                    if(response.body() != null){
                        Log.d("DetailViewModel", "a" + response.body()!!.data.title)
                        _mangaFullLiveData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<Manga>, t: Throwable) {
                    Log.d("DetailViewModel", t.message.toString())
                }
            })
    }
}