package com.example.anilist.viewModel

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anilist.pojo.Character
import com.example.anilist.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel: ViewModel() {
    private val _characterLiveData = MutableLiveData<Character>()
    val characterLiveData : LiveData<Character>
        get() = _characterLiveData

    private lateinit var loading: ProgressBar

    fun setLoadingBar(loading: ProgressBar){
        this.loading = loading
    }

    fun getCharacterLiveData(id : Int){
        RetrofitInstance.api.getCharactersById(id).enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                if(response.body() != null){
                    Log.d("CharacterViewModel", "success")
                    _characterLiveData.value = response.body()
                    loading.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                loading.visibility = View.GONE
                Log.d("CharacterViewModel", t.message.toString())
            }
        })
    }

}