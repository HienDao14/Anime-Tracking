package com.example.anilist.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anilist.pojo.Anime
import com.example.anilist.pojo.CalledAnime
import com.example.anilist.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _trendingAnimesLiveData= MutableLiveData<List<Anime>>()
    val trendingAnimesLiveData : LiveData<List<Anime>>
        get() = _trendingAnimesLiveData


    private val _seasonAnimeLiveData = MutableLiveData<List<Anime>>()
    val seasonAnimeLiveData : LiveData<List<Anime>>
        get() = _seasonAnimeLiveData

    private val _popularAllAnimeLiveData = MutableLiveData<List<Anime>>()
    val popularAllAnimeLiveData : LiveData<List<Anime>>
        get() = _popularAllAnimeLiveData

    private val _rankingAnimeLiveData = MutableLiveData<List<Anime>>()
    val rankingAnimeLiveData : LiveData<List<Anime>>
        get() = _rankingAnimeLiveData

    @SuppressLint("StaticFieldLeak")
    private var trendingLoading : ProgressBar?= null

    @SuppressLint("StaticFieldLeak")
    private var popularLoading : ProgressBar?= null

    @SuppressLint("StaticFieldLeak")
    private var allTimeLoading : ProgressBar?= null

    @SuppressLint("StaticFieldLeak")
    private var rankingLoading : ProgressBar?= null

    fun setUpTrendingLoadingBar(loading : ProgressBar){
        this.trendingLoading = loading
        this.trendingLoading!!.visibility = View.VISIBLE
    }

    fun setUpPopularLoadingBar(loading : ProgressBar){
        this.popularLoading = loading
        this.popularLoading!!.visibility = View.VISIBLE
    }

    fun setUpAllTimeLoadingBar(loading : ProgressBar){
        this.allTimeLoading = loading
        this.allTimeLoading!!.visibility = View.VISIBLE
    }

    fun setUpRankingLoadingBar(loading : ProgressBar){
        this.rankingLoading = loading
        this.rankingLoading!!.visibility = View.VISIBLE
    }
    fun getTrendingAnime(){
        RetrofitInstance.api.getTrendingNowAnime("airing").enqueue(object : Callback<CalledAnime>{
            override fun onResponse(call: Call<CalledAnime>, response: Response<CalledAnime>) {
                if(trendingLoading != null) trendingLoading!!.visibility = View.GONE
                if(response.body() != null){
                    _trendingAnimesLiveData.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<CalledAnime>, t: Throwable) {
                if(trendingLoading != null) trendingLoading!!.visibility = View.GONE
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }


    fun getSeasonAnime(){
        RetrofitInstance.api.getSeasonAnime().enqueue(object : Callback<CalledAnime>{
            override fun onResponse(call: Call<CalledAnime>, response: Response<CalledAnime>) {
                if(popularLoading != null) popularLoading!!.visibility = View.GONE
                if(response.body() != null){
                    _seasonAnimeLiveData.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<CalledAnime>, t: Throwable) {
                if(popularLoading != null) popularLoading!!.visibility = View.GONE
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }


    fun getPopularAllAnime(){
        RetrofitInstance.api.getTrendingNowAnime("bypopularity").enqueue(object : Callback<CalledAnime>{
            override fun onResponse(call: Call<CalledAnime>, response: Response<CalledAnime>) {
                if(allTimeLoading != null) allTimeLoading!!.visibility = View.GONE
                if(response.body() != null){
                    _popularAllAnimeLiveData.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<CalledAnime>, t: Throwable) {
                if(allTimeLoading != null) allTimeLoading!!.visibility = View.GONE
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getRankingAnime(page: Int){
        RetrofitInstance.api.getRankingAnime(page).enqueue(object : Callback<CalledAnime>{
            override fun onResponse(call: Call<CalledAnime>, response: Response<CalledAnime>) {
                rankingLoading!!.visibility = View.GONE
                if(response.body() != null){
                    _rankingAnimeLiveData.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<CalledAnime>, t: Throwable) {
                rankingLoading!!.visibility = View.GONE
                Log.d("HomeFragmentRanking", t.message.toString())
            }
        })
    }
}