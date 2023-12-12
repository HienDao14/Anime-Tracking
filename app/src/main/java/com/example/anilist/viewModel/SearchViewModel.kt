package com.example.anilist.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anilist.pojo.Anime
import com.example.anilist.pojo.CalledAnime
import com.example.anilist.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {

    private val _searchAnimeLiveData = MutableLiveData<List<Anime>>()
    val searchAnimeLiveData : LiveData<List<Anime>>
        get() = _searchAnimeLiveData

    private lateinit var searchQ : String

    fun setSearchText(searchText : String){
        this.searchQ = searchText
    }

    fun getSearchAnime(){
        RetrofitInstance.api.getSearchAnime(searchQ).enqueue(object : Callback<CalledAnime>{
            override fun onResponse(call: Call<CalledAnime>, response: Response<CalledAnime>) {
                if(response.body() != null){
                        _searchAnimeLiveData.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<CalledAnime>, t: Throwable) {
                Log.d("SearchFragment", t.message.toString())
            }
        })
    }
}